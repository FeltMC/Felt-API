package io.github.feltmc.feltapi.api.item.extensions;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public interface FeltItem extends FabricItem, ItemTagExtension, ArmorExtension, DamageableItemExtension {

    default boolean onDroppedByPlayer(ItemStack item, PlayerEntity player)
    {
        return true;
    }

    default Text getHighlightTip(ItemStack item, Text displayName)
    {
        return displayName;
    }

    default ActionResult onItemUseFirst(ItemStack stack, ItemUsageContext context)
    {
        return ActionResult.PASS;
    }

    default boolean isPiglinCurrency(ItemStack stack)
    {
        return stack.getItem() == PiglinBrain.BARTERING_ITEM;
    }

    default boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer)
    {
        return stack.getItem() instanceof ArmorItem armor && armor.getMaterial() == ArmorMaterials.GOLD;
    }
}
