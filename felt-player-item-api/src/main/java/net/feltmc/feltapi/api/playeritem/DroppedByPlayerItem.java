package net.feltmc.feltapi.api.playeritem;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface DroppedByPlayerItem {

    /**
     * Called when a player drops the item into the world, returning false from this
     * will prevent the item from being removed from the players inventory and
     * spawning in the world
     *
     * @param player The player that dropped the item
     * @param item   The item stack, before the item is removed.
     */
    boolean onDroppedByPlayer(ItemStack item, Player player);
}
