package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ArmorTickItem {
    default void onArmorTick(ItemStack stack, World level, PlayerEntity player) {
    }
}
