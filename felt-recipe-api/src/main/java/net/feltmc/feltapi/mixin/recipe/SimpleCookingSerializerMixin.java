package net.feltmc.feltapi.mixin.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleCookingSerializer.class)
public class SimpleCookingSerializerMixin {
    @Shadow @Final private SimpleCookingSerializer.CookieBaker<AbstractCookingRecipe> recipeFactory;

    @Shadow @Final private int cookingTime;

    @Inject(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lnet/minecraft/recipe/AbstractCookingRecipe;", at = @At(value = "HEAD"), cancellable = true)
    private void injectStackSupport(ResourceLocation recipeId, JsonObject json, CallbackInfoReturnable<AbstractCookingRecipe> cir){
        String string = GsonHelper.getAsString(json, "group", "");
        JsonElement jsonElement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.getAsJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonElement);
        JsonElement element = json.get("result");
        if (element instanceof JsonObject object){
            ItemStack itemStack = ShapedRecipe.itemStackFromJson(object);
            float f = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int i = GsonHelper.getAsInt(json, "cookingtime", this.cookingTime);
            cir.setReturnValue(this.recipeFactory.create(recipeId, string, ingredient, itemStack, f, i));
        }
    }
}
