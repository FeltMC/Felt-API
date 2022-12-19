package io.github.feltmc.feltapi.api.entityitem;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface EntityCustomItem {
    private Item self()
    {
        return (Item) this;
    }

    /**
     * Retrieves the normal 'lifespan' of this item when it is dropped on the ground
     * as a EntityItem. This is in ticks, standard result is 6000, or 5 mins.
     *
     * @param itemStack The current ItemStack
     * @param level     The level the entity is in
     * @return The normal lifespan in ticks.
     */
    default int getEntityLifespan(ItemStack itemStack, World level)
    {
        return 6000;
    }

    /**
     * Called when an item entity for this stack is destroyed. Note: The {@link ItemStack} can be retrieved from the item entity.
     *
     * @param itemEntity   The item entity that was destroyed.
     * @param damageSource Damage source that caused the item entity to "die".
     */
    default void onItemEntityDestroyed(ItemEntity itemEntity, DamageSource damageSource)
    {
        self().onItemEntityDestroyed(itemEntity);
    }
}
