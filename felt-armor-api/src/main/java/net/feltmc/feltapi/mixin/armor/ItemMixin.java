package net.feltmc.feltapi.mixin.armor;

import net.feltmc.feltapi.api.armor.ArmorEquipItem;
import net.feltmc.feltapi.api.armor.ArmorTickItem;
import net.feltmc.feltapi.api.armor.HorseArmorTickItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements ArmorEquipItem, ArmorTickItem, HorseArmorTickItem {
}
