package net.feltmc.feltapi.mixin.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.item.ItemStack;
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


    /*@Shadow @Final private SimpleCookingSerializer.CookieBaker<AbstractCookingRecipe> factory;

    @Shadow @Final private int defaultCookingTime;

    @Shadow @Final @Mutable
    private Codec<T> codec;
    @Unique
    private Codec<T> oldCodec;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(SimpleCookingSerializer.CookieBaker<T> cookieBaker, int i, CallbackInfo ci){
        this.oldCodec = this.codec;
        this.codec = ExtraCodecs.adaptJsonSerializer(, )
        this.codec = this.codec = RecordCodecBuilder.create((instance) -> {
            Products.P6 var10000 = instance.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.group;
            }), CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.category;
            }), Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.ingredient;
            }), BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.result;
            }), Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.experience;
            }), Codec.INT.fieldOf("cookingtime").orElse(i).forGetter((abstractCookingRecipe) -> {
                return abstractCookingRecipe.cookingTime;
            }));
            Objects.requireNonNull(cookieBaker);
            return var10000.apply(instance, cookieBaker::create);
        });
    }

    public T fromJson(ResourceLocation recipeId, JsonObject json, int defaultCookingTime) {
        String string = GsonHelper.getAsString(json, "group", "");
        CookingBookCategory cookingBookCategory = (CookingBookCategory)CookingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", (String)null), CookingBookCategory.MISC);
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.CODEC_NONEMPTY;
        String string2 = GsonHelper.getAsString(json, "result");
        ResourceLocation resourceLocation = new ResourceLocation(string2);
        ItemStack itemStack = new ItemStack((ItemLike)BuiltInRegistries.ITEM.getOptional(resourceLocation).orElseThrow(() -> {
            return new IllegalStateException("Item: " + string2 + " does not exist");
        }));
        float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
        int i = GsonHelper.getAsInt(json, "cookingtime", defaultCookingTime);
        return (T) this.factory.create(string, cookingBookCategory, ingredient, itemStack, f, i);
    }

    @Inject(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/world/item/crafting/AbstractCookingRecipe;", at = @At(value = "HEAD"), cancellable = true)
    private void injectStackSupport(ResourceLocation recipeId, JsonObject json, CallbackInfoReturnable<AbstractCookingRecipe> cir){
        Codecs
        String string = GsonHelper.getAsString(json, "group", "");
        CookingBookCategory cookingBookCategory = (CookingBookCategory)CookingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", (String)null), CookingBookCategory.MISC);
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);
        JsonElement element = json.get("result");
        if (element instanceof JsonObject object){
            ItemStack itemStack = ShapedRecipe.itemStackFromJson(object);
            float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int i = GsonHelper.getAsInt(json, "cookingtime", this.defaultCookingTime);
            cir.setReturnValue(this.factory.create(recipeId, string, cookingBookCategory, ingredient, itemStack, f, i));
        }
    }*/
}
