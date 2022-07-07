package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public interface ItemUseExtension {

    default void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    }

    default boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
    {
        return ItemStack.areItemsEqual(oldStack, newStack);
    }
}
