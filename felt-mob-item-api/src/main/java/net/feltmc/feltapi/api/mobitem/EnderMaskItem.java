package net.feltmc.feltapi.api.mobitem;

import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface EnderMaskItem {
    /**
     * Whether this Item can be used to hide player head for enderman.
     *
     * @param stack the ItemStack
     * @param player The player watching the enderman
     * @param endermanEntity The enderman that the player look
     * @return true if this Item can be used to hide player head for enderman
     */
    default boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
    {
        return stack.getItem() == Blocks.CARVED_PUMPKIN.asItem();
    }
}
