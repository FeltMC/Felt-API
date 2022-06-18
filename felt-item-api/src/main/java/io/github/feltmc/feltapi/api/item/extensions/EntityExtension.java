package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface EntityExtension {
    private Item self()
    {
        return (Item) this;
    }

    default boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
    {
        return false;
    }

    default int getEntityLifespan(ItemStack itemStack, World level)
    {
        return 6000;
    }

    default boolean hasCustomEntity(ItemStack stack)
    {
        return false;
    }

    @Nullable
    default Entity createEntity(World level, Entity location, ItemStack stack)
    {
        return null;
    }

    default boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        return false;
    }

    default void onItemEntityDestroyed(ItemEntity itemEntity, DamageSource damageSource)
    {
        self().onItemEntityDestroyed(itemEntity);
    }
}
