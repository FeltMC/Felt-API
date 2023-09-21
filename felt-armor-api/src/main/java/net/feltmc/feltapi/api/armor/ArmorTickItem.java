package net.feltmc.feltapi.api.armor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ArmorTickItem {
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
    void onArmorTick(ItemStack stack, Level level, Player player);
}
