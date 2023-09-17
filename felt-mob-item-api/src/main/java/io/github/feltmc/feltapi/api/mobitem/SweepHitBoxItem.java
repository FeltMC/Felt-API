package io.github.feltmc.feltapi.api.mobitem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;

import javax.annotation.Nonnull;

public interface SweepHitBoxItem {
    /**
     * Get a bounding box ({@link Box}) of a sweep attack.
     *
     * @param stack the stack held by the player.
     * @param player the performing the attack the attack.
     * @param target the entity targeted by the attack.
     * @return the bounding box.
     */
    @Nonnull
    default Box getSweepHitBox(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull Entity target)
    {
        return target.getBoundingBox().expand(1.0D, 0.25D, 1.0D);
    }
}
