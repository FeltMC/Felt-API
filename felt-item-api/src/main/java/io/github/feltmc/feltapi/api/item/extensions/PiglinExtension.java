package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;

public interface PiglinExtension {
    default boolean isPiglinCurrency(ItemStack stack)
    {
        return stack.getItem() == PiglinBrain.BARTERING_ITEM;
    }

    default boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        return stack.getItem() instanceof ArmorItem armor && armor.getMaterial() == ArmorMaterials.GOLD;
    }
}
