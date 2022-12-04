package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;

public interface ArmorEquipItem {

    /**
     * Determines if the specific ItemStack can be placed in the specified armor
     * slot, for the entity.
     *
     * @param stack     The ItemStack
     * @param armorType Armor slot to be verified.
     * @param entity    The entity trying to equip the armor
     * @return True if the given ItemStack can be inserted in the slot
     */
    default boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity)
    {
        return MobEntity.getPreferredEquipmentSlot(stack) == armorType;
    }
}
