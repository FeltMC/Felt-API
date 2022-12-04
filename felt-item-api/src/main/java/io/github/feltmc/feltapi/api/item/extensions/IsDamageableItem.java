package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IsDamageableItem {

    /**
     * Used to test if this item can be damaged, but with the ItemStack in question.
     * Please note that in some cases no ItemStack is available, so the stack-less method will be used.
     *
     * @param stack       ItemStack in the Chest slot of the entity.
     */
    default boolean isDamageable(ItemStack stack)
    {
        return ((Item)this).isDamageable();
    }

    /**
     * Return if this itemstack is damaged. Note only called if
     * {@link ItemStack#isDamageable()} is true.
     *
     * @param stack the stack
     * @return if the stack is damaged
     */
    default boolean isDamaged(ItemStack stack)
    {
        return stack.getDamage() > 0;
    }
}
