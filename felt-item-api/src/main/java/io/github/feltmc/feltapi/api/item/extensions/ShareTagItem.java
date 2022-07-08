package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import javax.annotation.Nullable;

public interface ShareTagItem {
    default NbtCompound getShareTag(ItemStack stack) {
        return stack.getNbt();
    }

    default void readShareTag(ItemStack stack, @Nullable NbtCompound nbt) {
        stack.setNbt(nbt);
    }
}
