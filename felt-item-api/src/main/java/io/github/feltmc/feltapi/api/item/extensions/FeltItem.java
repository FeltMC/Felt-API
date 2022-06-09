package io.github.feltmc.feltapi.api.item.extensions;

import io.github.feltmc.feltapi.api.item.ToolAction;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface FeltItem extends FabricItem, ItemTagExtension, ArmorExtension, DamageableItemExtension, ContainerItemExtension {
    private Item self()
    {
        return (Item) this;
    }

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

    default boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player)
    {
        return false;
    }

    default void onUsingTick(ItemStack stack, LivingEntity player, int count) {
    }

    default boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        return false;
    }

    default int getEntityLifespan(ItemStack itemStack, World level)
    {
        return 6000;
    }

    default boolean hasCustomEntity(ItemStack stack)
    {
        return false;
    }

    @Nullable
    default Entity createEntity(World level, Entity location, ItemStack stack)
    {
        return null;
    }

    default boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        return false;
    }

    default Collection<ItemGroup> getGroups()
    {
        return Collections.singletonList(self().getGroup());
    }

    default boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
    }

    default boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        return false;
    }

    default int getItemStackLimit(ItemStack stack)
    {
        return self().getMaxCount();
    }


    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
    {
        return !oldStack.equals(newStack); // !ItemStack.areItemStacksEqual(oldStack, newStack);
    }

    default boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack)
    {
        // Fix MC-176559 mending resets mining progress / breaking animation
        if (!newStack.isOf(oldStack.getItem()))
            return true;

        if (!newStack.isDamageable() || !oldStack.isDamageable())
            return !ItemStack.areNbtEqual(newStack, oldStack);

        NbtCompound newTag = newStack.getNbt();
        NbtCompound oldTag = oldStack.getNbt();

        if (newTag == null || oldTag == null)
            return !(newTag == null && oldTag == null);

        Set<String> newKeys = new HashSet<>(newTag.getKeys());
        Set<String> oldKeys = new HashSet<>(oldTag.getKeys());

        newKeys.remove(ItemStack.DAMAGE_KEY);
        oldKeys.remove(ItemStack.DAMAGE_KEY);

        if (!newKeys.equals(oldKeys))
            return true;

        return !newKeys.stream().allMatch(key -> Objects.equals(newTag.get(key), oldTag.get(key)));
        // return !(newStack.is(oldStack.getItem()) && ItemStack.tagMatches(newStack, oldStack)
        //         && (newStack.isDamageableItem() || newStack.getDamageValue() == oldStack.getDamageValue()));
    }

    default boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
    {
        return ItemStack.areItemsEqual(oldStack, newStack);
    }

    default void onHorseArmorTick(ItemStack stack, World level, MobEntity horse) {
    }

    default void onItemEntityDestroyed(ItemEntity itemEntity, DamageSource damageSource)
    {
        self().onItemEntityDestroyed(itemEntity);
    }

    default boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
    {
        return stack.getItem() == Blocks.CARVED_PUMPKIN.asItem();
    }

    default boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        return false;
    }

    default boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return false;
    }

    default boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer)
    {
        return stack.isOf(Items.LEATHER_BOOTS);
    }

    @Nonnull
    default Box getSweepHitBox(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull Entity target)
    {
        return target.getBoundingBox().expand(1.0D, 0.25D, 1.0D);
    }

    default int getDefaultTooltipHideFlags(@Nonnull ItemStack stack)
    {
        return 0;
    }


    @Nullable // read javadoc to find a potential problem
    default FoodComponent getFoodComponenet(ItemStack stack, @Nullable LivingEntity entity)
    {
        return self().getFoodComponent();
    }

    default boolean canPerformAction(ItemStack stack, ToolAction toolAction)
    {
        return false;
    }
}
