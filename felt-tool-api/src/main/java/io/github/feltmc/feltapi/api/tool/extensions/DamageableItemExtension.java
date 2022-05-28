package io.github.feltmc.feltapi.api.tool.extensions;

import net.minecraft.item.ItemStack;

public interface DamageableItemExtension {
    default int getMaxDamage(ItemStack stack) {
        return stack.getItem().getMaxDamage();
    }
}
