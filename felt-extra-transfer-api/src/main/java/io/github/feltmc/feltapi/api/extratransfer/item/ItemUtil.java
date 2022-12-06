package io.github.feltmc.feltapi.api.extratransfer.item;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

import javax.annotation.Nonnull;

public class ItemUtil {

    public static ItemStack insertItemStacked(Storage<ItemVariant> inventory, ItemStack stack, boolean simulate){
        if (inventory == null || stack.isEmpty())
            return stack;

        Transaction transaction = Transaction.openOuter();
        long inserted = inventory.insert(ItemVariant.of(stack), stack.getCount(), transaction);
        if (!simulate) transaction.commit();
        if (inserted == 0) return stack;
        if (inserted == stack.getCount()) return ItemStack.EMPTY;
        return ItemVariant.of(stack).toStack((int) (stack.getCount() - inserted));
    }

    public static ItemStack copyStackWithSize(ItemStack stack, int count){
        ItemStack copy = stack.copy();
        copy.setCount(count);
        return copy;
    }
}
