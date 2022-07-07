package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface ArmorExtension {
    default String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return null;
    }

    default void onArmorTick(ItemStack stack, World level, PlayerEntity player) {
    }

    default boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity)
    {
        return MobEntity.getPreferredEquipmentSlot(stack) == armorType;
    }
}
