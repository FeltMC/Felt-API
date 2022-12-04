package io.github.feltmc.feltapi.api.item.extensions;

import io.github.fabricators_of_create.porting_lib.extensions.ItemExtensions;
import io.github.fabricators_of_create.porting_lib.item.BlockUseBypassingItem;
import io.github.fabricators_of_create.porting_lib.item.CustomMaxCountItem;
import io.github.fabricators_of_create.porting_lib.item.EntitySwingListenerItem;
import io.github.fabricators_of_create.porting_lib.item.EntityTickListenerItem;
import io.github.fabricators_of_create.porting_lib.item.EquipmentItem;
import io.github.fabricators_of_create.porting_lib.item.PiglinsNeutralItem;
import io.github.fabricators_of_create.porting_lib.item.ReequipAnimationItem;
import io.github.fabricators_of_create.porting_lib.item.UseFirstBehaviorItem;
import io.github.fabricators_of_create.porting_lib.item.WalkOnSnowItem;
import io.github.fabricators_of_create.porting_lib.util.ArmorTextureItem;
import io.github.fabricators_of_create.porting_lib.util.ContinueUsingItem;
import io.github.fabricators_of_create.porting_lib.util.DamageableItem;
import io.github.fabricators_of_create.porting_lib.util.ShieldBlockItem;
import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import io.github.fabricators_of_create.porting_lib.util.UsingTickItem;
import io.github.fabricators_of_create.porting_lib.util.XpRepairItem;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface PortingLibInterfaces extends BlockUseBypassingItem, CustomMaxCountItem, EntitySwingListenerItem, ItemExtensions, EntityTickListenerItem, EquipmentItem, PiglinsNeutralItem, ReequipAnimationItem, UseFirstBehaviorItem, WalkOnSnowItem, ArmorTextureItem, ContinueUsingItem, DamageableItem, ShieldBlockItem, UsingTickItem, XpRepairItem {
    @Override
    default boolean shouldBypass(BlockState state, BlockPos pos, World level, PlayerEntity player, Hand hand){
        return false;
    }

    @Override
    default boolean onEntitySwing(ItemStack stack, LivingEntity entity){
        return false;
    }

    @Override
    default boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity){
        return false;
    }

    @Override
    default ActionResult onItemUseFirst(ItemStack stack, ItemUsageContext context){
        return ActionResult.PASS;
    }

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

    @Override
    default String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type){
        String var10000 = ((ArmorItem)stack.getItem()).getMaterial().getName();
        return "textures/models/armor/" + var10000 + "_layer_" + (slot == EquipmentSlot.LEGS ? 2 : 1) + (type == null ? "" : "_" + type) + ".png";
    }
}
