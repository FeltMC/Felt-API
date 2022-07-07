package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface MiscExtension {
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


    default Collection<ItemGroup> getGroups()
    {
        return Collections.singletonList(self().getGroup());
    }

    default boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        return true;
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

    default void onHorseArmorTick(ItemStack stack, World level, MobEntity horse) {
    }

    default boolean isEnderMask(ItemStack stack, PlayerEntity player, EndermanEntity endermanEntity)
    {
        return stack.getItem() == Blocks.CARVED_PUMPKIN.asItem();
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
}
