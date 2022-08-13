package io.github.feltmc.feltapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Redirect(method = "craftRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;increment(I)V"))
    private static void injectAmount(ItemStack instance, int increment, Recipe<?> recipe, DefaultedList<ItemStack> nonNullList, int i){
        instance.increment(recipe.getOutput().getCount());
    }

    @ModifyReturnValue(method = "canAcceptRecipeOutput", at = @At(value = "RETURN", ordinal = 5))
    private static boolean canFit(boolean original, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getOutput().getCount() <= nonNullList.get(2).getMaxCount();
    }

    @ModifyReturnValue(method = "canAcceptRecipeOutput", at = @At(value = "RETURN", ordinal = 4))
    private static boolean canFit2(boolean original, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getOutput().getCount() <= i;
    }
}
