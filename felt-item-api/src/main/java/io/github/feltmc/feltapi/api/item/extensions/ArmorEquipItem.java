package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;

public interface ArmorEquipItem {

    default boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity)
    {
        return MobEntity.getPreferredEquipmentSlot(stack) == armorType;
    }
}
