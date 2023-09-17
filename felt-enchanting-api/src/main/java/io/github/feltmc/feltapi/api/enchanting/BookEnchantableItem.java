package io.github.feltmc.feltapi.api.enchanting;

import net.minecraft.item.ItemStack;

public interface BookEnchantableItem {
    /**
     * Allow or forbid the specific book/item combination as an anvil enchant
     *
     * @param stack The item
     * @param book  The book
     * @return if the enchantment is allowed
     */
    default boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }
}
