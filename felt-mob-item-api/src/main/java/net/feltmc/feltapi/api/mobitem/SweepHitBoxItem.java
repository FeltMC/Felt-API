package net.feltmc.feltapi.api.mobitem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public interface SweepHitBoxItem {
    /**
     * Get a bounding box ({@link AABB}) of a sweep attack.
     *
     * @param stack the stack held by the player.
     * @param player the performing the attack the attack.
     * @param target the entity targeted by the attack.
     * @return the bounding box.
     */
    @NotNull
    default AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target)
    {
        return target.getBoundingBox().inflate(1.0D, 0.25D, 1.0D);
    }
}
