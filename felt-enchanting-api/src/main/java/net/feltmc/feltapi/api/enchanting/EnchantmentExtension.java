package net.feltmc.feltapi.api.enchanting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchantmentExtension  {
    default boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem().canApplyAtEnchantingTable(stack, (Enchantment) this);
    }
}
