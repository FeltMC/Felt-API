package io.github.feltmc.feltapi.api.tool.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import java.sql.Ref;

public interface ArmorTextureExtension {
    default String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return null;
    }
}
