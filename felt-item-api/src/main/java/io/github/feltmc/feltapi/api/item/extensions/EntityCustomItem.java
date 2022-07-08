package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface EntityCustomItem {
    private Item self()
    {
        return (Item) this;
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

    default void onItemEntityDestroyed(ItemEntity itemEntity, DamageSource damageSource)
    {
        self().onItemEntityDestroyed(itemEntity);
    }
}
