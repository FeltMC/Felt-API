package io.github.feltmc.feltapi.api.item.extensions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface ContainerItemExtension {
    private Item self()
    {
        return (Item) this;
    }

    default ItemStack getContainerItem(ItemStack itemStack)
    {
        if (!hasContainerItem(itemStack))
        {
            return ItemStack.EMPTY;
        }
        return new ItemStack(self().getRecipeRemainder());
    }

    default boolean hasContainerItem(ItemStack stack)
    {
        return self().hasRecipeRemainder();
    }
}
