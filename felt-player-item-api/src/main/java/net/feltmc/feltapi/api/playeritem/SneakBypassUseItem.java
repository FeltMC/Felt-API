package net.feltmc.feltapi.api.playeritem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;

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
    boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player);
}
