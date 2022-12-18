package io.github.feltmc.feltapi.api.armor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ArmorTickItem {
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    void onArmorTick(ItemStack stack, World level, PlayerEntity player);
}
