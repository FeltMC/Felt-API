package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface RepairableItem {
    /**
     * Called by CraftingManager to determine if an item is reparable.
     *
     * @return True if reparable
     */
    default boolean isRepairable(ItemStack stack){
        return ((Item)this).isDamageable();
    }
}
