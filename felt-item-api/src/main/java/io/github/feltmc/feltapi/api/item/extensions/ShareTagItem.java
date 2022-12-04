package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import javax.annotation.Nullable;

public interface ShareTagItem {
    /**
     * Override this method to change the NBT data being sent to the client. You
     * should ONLY override this when you have no other choice, as this might change
     * behavior client side!
     *
     * Note that this will sometimes be applied multiple times, the following MUST
     * be supported:
     *   Item item = stack.getItem();
     *   NBTTagCompound nbtShare1 = item.getNBTShareTag(stack);
     *   stack.setTagCompound(nbtShare1);
     *   NBTTagCompound nbtShare2 = item.getNBTShareTag(stack);
     *   assert nbtShare1.equals(nbtShare2);
     *
     * @param stack The stack to send the NBT tag for
     * @return The NBT tag
     */
    default NbtCompound getShareTag(ItemStack stack) {
        return stack.getNbt();
    }

    /**
     * Override this method to decide what to do with the NBT data received from
     * getNBTShareTag().
     *
     * @param stack The stack that received NBT
     * @param nbt   Received NBT, can be null
     */
    default void readShareTag(ItemStack stack, @Nullable NbtCompound nbt) {
        stack.setNbt(nbt);
    }
}
