package net.feltmc.feltapi.mixin.playeritem;

import net.feltmc.feltapi.api.playeritem.PlayerItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements PlayerItem {
}
