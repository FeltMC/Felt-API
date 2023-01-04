package io.github.feltmc.feltapi.api.blockbreakreset;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface BlockBreakResetItem {
    /**
     * Called when the player is mining a block and the item in his hand changes.
     * Allows to not reset blockbreaking if only NBT or similar changes.
     *
     * @param oldStack The old stack that was used for mining. Item in players main
     *                 hand
     * @param newStack The new stack
     * @return True to reset block break progress
     */
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
}
