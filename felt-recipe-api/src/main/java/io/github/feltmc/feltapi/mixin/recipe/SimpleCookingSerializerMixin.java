package io.github.feltmc.feltapi.mixin.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CookingRecipeSerializer.class)
public class SimpleCookingSerializerMixin {
    @Shadow @Final private CookingRecipeSerializer.RecipeFactory<AbstractCookingRecipe> recipeFactory;

    @Shadow @Final private int cookingTime;

    @Inject(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "HEAD"), cancellable = true)
    private void injectStackSupport(Identifier recipeId, JsonObject json, CallbackInfoReturnable<AbstractCookingRecipe> cir){
        String string = JsonHelper.getString(json, "group", "");
        JsonElement jsonElement = JsonHelper.hasArray(json, "ingredient") ? JsonHelper.getArray(json, "ingredient") : JsonHelper.getObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);
        JsonElement element = json.get("result");
        if (element instanceof JsonObject object){
            ItemStack itemStack = ShapedRecipe.outputFromJson(object);
            float f = JsonHelper.getFloat(json, "experience", 0.0F);
            int i = JsonHelper.getInt(json, "cookingtime", this.cookingTime);
            cir.setReturnValue(this.recipeFactory.create(recipeId, string, ingredient, itemStack, f, i));
        }
    }
}
