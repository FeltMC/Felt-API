package net.feltmc.feltapi.mixin.mobitem;

import net.feltmc.feltapi.api.mobitem.MobItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements MobItem {
}
