package net.feltmc.feltapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Redirect(method = "craftRecipe", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;increment(I)V"))
    private static void injectAmount(ItemStack instance, int increment, Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        instance.grow(recipe.getResultItem().getCount());
    }

    @ModifyReturnValue(method = "canAcceptRecipeOutput", at = @At(value = "RETURN", ordinal = 5))
    private static boolean canFit(boolean original, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem().getCount() <= nonNullList.get(2).getMaxStackSize();
    }

    @ModifyReturnValue(method = "canAcceptRecipeOutput", at = @At(value = "RETURN", ordinal = 4))
    private static boolean canFit2(boolean original, @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem().getCount() <= i;
    }
}
