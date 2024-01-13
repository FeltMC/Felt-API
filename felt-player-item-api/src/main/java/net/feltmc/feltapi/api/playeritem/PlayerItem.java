package net.feltmc.feltapi.api.playeritem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;

public interface PlayerItem {
    /**
     * Called when a player drops the item into the world, returning false from this
     * will prevent the item from being removed from the players inventory and
     * spawning in the world
     *
     * @param player The player that dropped the item
     * @param item   The item stack, before the item is removed.
     */
    default boolean onDroppedByPlayer(ItemStack item, Player player){
        return true;
    }

    /**
     *
     * Should this item, when held, allow sneak-clicks to pass through to the
     * underlying block?
     *
     * @param world  The level
     * @param pos    Block position in level
     * @param player The Player that is wielding the item
     */
    default boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player){
        return false;
    }
}
