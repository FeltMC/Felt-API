package net.feltmc.feltapi.mixin.recipe;

import com.google.gson.*;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(SimpleCookingSerializer.class)
public class SimpleCookingSerializerMixin<T extends AbstractCookingRecipe> {


    @Shadow @Final private SimpleCookingSerializer.CookieBaker<AbstractCookingRecipe> factory;

    @Shadow @Final private int defaultCookingTime;

    @Shadow @Final @Mutable
    private Codec<T> codec;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(SimpleCookingSerializer.CookieBaker<T> cookieBaker, int i, CallbackInfo ci){
        Codec<ItemStack> stackCodec = ExtraCodecs.adaptJsonSerializer(j -> {
            if (j instanceof JsonObject jsonObject){
                return itemStackFromJson(jsonObject);
            } else if (j.isJsonPrimitive()){
                Item item = itemFromJson(j);
                return  new ItemStack(item);
            }
            return ItemStack.EMPTY;
        }, itemStack -> {
            if (itemStack.getCount() > 1){
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("item", BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString());
                jsonObject.addProperty("count", itemStack.getCount());
                return jsonObject;
            } else {
                return new JsonPrimitive(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString());
            }
        });
        this.codec = RecordCodecBuilder.create((instance) -> {
            var var10000 = instance.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(AbstractCookingRecipe::getGroup),
                    CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(AbstractCookingRecipe::category),
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.getIngredients().get(0);
            }), stackCodec.fieldOf("result").forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.getResultItem(null);
            }), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(AbstractCookingRecipe::getExperience),
                    Codec.INT.fieldOf("cookingtime").orElse(i).forGetter(AbstractCookingRecipe::getCookingTime));
            Objects.requireNonNull(cookieBaker);
            return var10000.apply(instance, cookieBaker::create);
        });
    }

    @Unique
    private ItemStack itemStackFromJson(JsonObject stackObject) {
        Item item = itemFromJson(stackObject);
        if (stackObject.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(stackObject, "count", 1);
            if (i < 1) {
                throw new JsonSyntaxException("Invalid output count: " + i);
            } else {
                return new ItemStack(item, i);
            }
        }
    }

    @Unique
    private Item itemFromJson(JsonElement itemObject) {
        String string = itemObject instanceof  JsonObject jsonObject ? GsonHelper.getAsString(jsonObject, "item") : itemObject.getAsString();
        Item item = (Item)BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(string)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + string + "'");
        });
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item;
        }
    }
}
