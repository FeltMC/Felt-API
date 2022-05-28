package io.github.feltmc.feltapi.api.tool.extensions;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import javax.annotation.Nullable;
import java.sql.Ref;

public interface ItemTagExtension {
    default NbtCompound getShareTag(ItemStack stack) {
        return stack.getNbt();
    }

    default void readShareTag(ItemStack stack, @Nullable NbtCompound nbt) {
        stack.setNbt(nbt);
    }
}
