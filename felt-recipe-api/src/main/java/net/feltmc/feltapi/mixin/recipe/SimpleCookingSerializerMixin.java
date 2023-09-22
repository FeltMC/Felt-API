package net.feltmc.feltapi.mixin.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleCookingSerializer.class)
public class SimpleCookingSerializerMixin {
    @Shadow @Final private SimpleCookingSerializer.CookieBaker<AbstractCookingRecipe> factory;

    @Shadow @Final private int defaultCookingTime;

    @Inject(method = "fromJson(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/JsonObject;)Lnet/minecraft/world/item/crafting/AbstractCookingRecipe;", at = @At(value = "HEAD"), cancellable = true)
    private void injectStackSupport(ResourceLocation recipeId, JsonObject json, CallbackInfoReturnable<AbstractCookingRecipe> cir){
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
    }
}
