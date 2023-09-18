package net.feltmc.feltapi.api.mobitem;

import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;

public interface PiglinCurrencyItem {
    default boolean isPiglinCurrency(ItemStack stack)
    {
        return stack.getItem() == PiglinBrain.BARTERING_ITEM;
    }
}
