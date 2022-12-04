package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.ItemStack;

public interface XpRepairItem {
    /**
     * Determines the amount of durability the mending enchantment
     * will repair, on average, per point of experience.
     */
    default float getXpRepairRatio(ItemStack stack)
    {
        return 2f;
    }
}
