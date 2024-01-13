package net.feltmc.feltapi.api.mobitem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public interface MobItem {
    default boolean isPiglinCurrency(ItemStack stack)
    {
        return stack.getItem() == PiglinAi.BARTERING_ITEM;
    }
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

    /**
     * Whether this Item can be used to hide player head for enderman.
     *
     * @param stack the ItemStack
     * @param player The player watching the enderman
     * @param endermanEntity The enderman that the player look
     * @return true if this Item can be used to hide player head for enderman
     */
    default boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity)
    {
        return stack.getItem() == Blocks.CARVED_PUMPKIN.asItem();
    }
}
