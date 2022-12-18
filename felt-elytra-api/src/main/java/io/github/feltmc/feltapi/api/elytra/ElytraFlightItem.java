package io.github.feltmc.feltapi.api.elytra;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface ElytraFlightItem {
    /**
     * Used to determine if the player can use Elytra flight.
     * This is called Client and Server side.
     *
     * @param stack The ItemStack in the Chest slot of the entity.
     * @param entity The entity trying to fly.
     * @return True if the entity can use Elytra flight.
     */
    default boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        return false;
    }

    /**
     * Used to determine if the player can continue Elytra flight,
     * this is called each tick, and can be used to apply ItemStack damage,
     * consume Energy, or what have you.
     * For example the Vanilla implementation of this, applies damage to the
     * ItemStack every 20 ticks.
     *
     * @param stack       ItemStack in the Chest slot of the entity.
     * @param entity      The entity currently in Elytra flight.
     * @param flightTicks The number of ticks the entity has been Elytra flying for.
     * @return True if the entity should continue Elytra flight or False to stop.
     */
    default boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return false;
    }
}
