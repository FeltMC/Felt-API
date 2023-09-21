package net.feltmc.feltapi.api.mobitem;

import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;

public interface PiglinCurrencyItem {
    default boolean isPiglinCurrency(ItemStack stack)
    {
        return stack.getItem() == PiglinAi.BARTERING_ITEM;
    }
}
