package io.github.feltmc.feltapi.api.item.extensions;

import io.github.fabricators_of_create.porting_lib.extensions.ItemExtensions;
import io.github.fabricators_of_create.porting_lib.item.CustomMaxCountItem;
import io.github.fabricators_of_create.porting_lib.item.EquipmentItem;
import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;

public interface FeltItem extends FabricItem, ItemTagExtension, ArmorExtension, DamageableItemExtension, ContainerItemExtension, ElytraExtension, EntityExtension, ItemUseExtension, PiglinExtension, MiscExtension, CustomMaxCountItem, ItemExtensions, EquipmentItem {

    @Nullable
    @Override
    default EquipmentSlot getEquipmentSlot(ItemStack stack) {
        Item item = stack.getItem();
        if (!stack.isOf(Items.CARVED_PUMPKIN) && (!(item instanceof BlockItem) || !(((BlockItem)item).getBlock() instanceof AbstractSkullBlock))) {
            if (item instanceof ArmorItem) {
                return ((ArmorItem)item).getSlotType();
            } else if (stack.isOf(Items.ELYTRA)) {
                return EquipmentSlot.CHEST;
            } else {
                return stack.canPerformAction(ToolActions.SHIELD_BLOCK) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            }
        } else {
            return EquipmentSlot.HEAD;
        }
    }

    @Override
    default int getItemStackLimit(ItemStack stack)
    {
        return stack.getItem().getMaxCount();
    }
}
