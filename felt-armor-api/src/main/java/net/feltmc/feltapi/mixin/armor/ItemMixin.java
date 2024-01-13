package net.feltmc.feltapi.mixin.armor;

import net.feltmc.feltapi.api.armor.ArmorItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements ArmorItem {
}
