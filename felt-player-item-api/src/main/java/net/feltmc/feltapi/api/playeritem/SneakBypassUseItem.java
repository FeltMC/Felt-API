package net.feltmc.feltapi.api.playeritem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public interface SneakBypassUseItem {
    /**
     *
     * Should this item, when held, allow sneak-clicks to pass through to the
     * underlying block?
     *
     * @param world  The level
     * @param pos    Block position in level
     * @param player The Player that is wielding the item
     */
    boolean doesSneakBypassUse(ItemStack stack, WorldView world, BlockPos pos, PlayerEntity player);
}
