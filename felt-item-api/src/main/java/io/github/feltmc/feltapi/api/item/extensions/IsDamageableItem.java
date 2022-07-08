package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IsDamageableItem {

    default boolean isDamageable(ItemStack stack)
    {
        return ((Item)this).isDamageable();
    }

    default boolean isDamaged(ItemStack stack)
    {
        return stack.getDamage() > 0;
    }
}
