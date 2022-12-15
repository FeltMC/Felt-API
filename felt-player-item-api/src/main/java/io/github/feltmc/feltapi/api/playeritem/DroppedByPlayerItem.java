package io.github.feltmc.feltapi.api.playeritem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface DroppedByPlayerItem {

    /**
     * Called when a player drops the item into the world, returning false from this
     * will prevent the item from being removed from the players inventory and
     * spawning in the world
     *
     * @param player The player that dropped the item
     * @param item   The item stack, before the item is removed.
     */
    default boolean onDroppedByPlayer(ItemStack item, PlayerEntity player)
    {
        return true;
    }
}
