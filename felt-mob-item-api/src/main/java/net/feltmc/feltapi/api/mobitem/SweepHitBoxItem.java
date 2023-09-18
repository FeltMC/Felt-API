package net.feltmc.feltapi.api.mobitem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.NotNull;

public interface SweepHitBoxItem {
    /**
     * Get a bounding box ({@link Box}) of a sweep attack.
     *
     * @param stack the stack held by the player.
     * @param player the performing the attack the attack.
     * @param target the entity targeted by the attack.
     * @return the bounding box.
     */
    @NotNull
    default Box getSweepHitBox(@NotNull ItemStack stack, @NotNull PlayerEntity player, @NotNull Entity target)
    {
        return target.getBoundingBox().expand(1.0D, 0.25D, 1.0D);
    }
}
