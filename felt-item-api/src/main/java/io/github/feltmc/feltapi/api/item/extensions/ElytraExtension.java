package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public interface ElytraExtension {
    default boolean canElytraFly(ItemStack stack, LivingEntity entity)
    {
        return false;
    }

    default boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks)
    {
        return false;
    }
}
