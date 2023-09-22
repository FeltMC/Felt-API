package net.feltmc.feltapi.mixin.recipe;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Redirect(method = "burn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;grow(I)V"))
    private static void injectAmount(ItemStack instance, int increment, RegistryAccess registryAccess, Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        instance.grow(recipe.getResultItem(registryAccess).getCount());
    }

    @ModifyReturnValue(method = "canBurn", at = @At(value = "RETURN", ordinal = 5))
    private static boolean canFit(boolean original, RegistryAccess registryAccess,  @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem(registryAccess).getCount() <= nonNullList.get(2).getMaxStackSize();
    }

    @ModifyReturnValue(method = "canBurn", at = @At(value = "RETURN", ordinal = 4))
    private static boolean canFit2(boolean original, RegistryAccess registryAccess,  @Nullable Recipe<?> recipe, NonNullList<ItemStack> nonNullList, int i){
        return nonNullList.get(2).getCount() + recipe.getResultItem(registryAccess).getCount() <= i;
    }
}
