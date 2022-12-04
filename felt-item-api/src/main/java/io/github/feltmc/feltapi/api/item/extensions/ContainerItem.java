package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ContainerItem {
    private Item self()
    {
        return (Item) this;
    }

    /**
     * ItemStack sensitive version of getContainerItem. Returns a full ItemStack
     * instance of the result.
     *
     * @param itemStack The current ItemStack
     * @return The resulting ItemStack
     */
    default ItemStack getContainerItem(ItemStack itemStack)
    {
        if (!hasContainerItem(itemStack))
        {
            return ItemStack.EMPTY;
        }
        return new ItemStack(self().getRecipeRemainder());
    }

    /**
     * ItemStack sensitive version of hasContainerItem
     *
     * @param stack The current item stack
     * @return True if this item has a 'container'
     */
    default boolean hasContainerItem(ItemStack stack)
    {
        return self().hasRecipeRemainder();
    }


    /**
     * Called by CraftingManager to determine if an item is reparable.
     *
     * @return True if reparable
     */
    default boolean isRepairable(){
        return false;
    }
}
