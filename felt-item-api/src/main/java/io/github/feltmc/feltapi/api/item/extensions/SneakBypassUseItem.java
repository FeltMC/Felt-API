package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public interface SneakBypassUseItem {
    default boolean doesSneakBypassUse(ItemStack stack, WorldView world, BlockPos pos, PlayerEntity player) {
        return false;
    }
}
