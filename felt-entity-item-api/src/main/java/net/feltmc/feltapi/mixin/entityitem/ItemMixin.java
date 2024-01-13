package net.feltmc.feltapi.mixin.entityitem;

import net.feltmc.feltapi.api.entityitem.EntityCustomItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements EntityCustomItem {
}
