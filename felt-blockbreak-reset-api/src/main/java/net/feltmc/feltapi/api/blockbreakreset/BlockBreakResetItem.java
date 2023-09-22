package net.feltmc.feltapi.api.blockbreakreset;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

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
        if (!newStack.is(oldStack.getItem()))
            return true;

        if (!newStack.isDamageableItem() || !oldStack.isDamageableItem())
            return !ItemStack.isSameItemSameTags(newStack, oldStack);

        CompoundTag newTag = newStack.getTag();
        CompoundTag oldTag = oldStack.getTag();

        if (newTag == null || oldTag == null)
            return !(newTag == null && oldTag == null);

        Set<String> newKeys = new HashSet<>(newTag.getAllKeys());
        Set<String> oldKeys = new HashSet<>(oldTag.getAllKeys());

        newKeys.remove(ItemStack.TAG_DAMAGE);
        oldKeys.remove(ItemStack.TAG_DAMAGE);

        if (!newKeys.equals(oldKeys))
            return true;

        return !newKeys.stream().allMatch(key -> Objects.equals(newTag.get(key), oldTag.get(key)));
        // return !(newStack.is(oldStack.getItem()) && ItemStack.tagMatches(newStack, oldStack)
        //         && (newStack.isDamageableItem() || newStack.getDamageValue() == oldStack.getDamageValue()));
    }
}
