package io.github.feltmc.feltapi.api.extratransfer.item;

import io.github.feltmc.feltapi.api.extratransfer.fluid.StoredFluid;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;

import javax.annotation.Nonnull;

@SuppressWarnings("UnstableApiUsage")
public class ItemUtil {

    public static void giveItemToPlayer(PlayerEntity player, ItemStack stack){
        Transaction transaction = Transaction.openOuter();
        PlayerInventoryStorage.of(player).offerOrDrop(ItemVariant.of(stack), stack.getCount(), transaction);
        transaction.commit();
    }

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

    public static long insertItem(Storage<ItemVariant> storage, ItemStack stack, boolean simulate){
        Transaction transaction = Transaction.openOuter();
        long insert = storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
        if (!simulate) transaction.commit();
        return insert;
    }

    public static long extractItem(Storage<ItemVariant> storage, ItemStack stack, boolean simulate){
        Transaction transaction = Transaction.openOuter();
        long extract = storage.extract(ItemVariant.of(stack), stack.getCount(), transaction);
        if (!simulate) transaction.commit();
        return extract;
    }

    public static ItemStack extractAnyItem(Storage<ItemVariant> storage, long maxAmount, Transaction tx) {
        ItemStack stack = ItemStack.EMPTY;
        if (!storage.supportsExtraction()) return stack;
        for (StorageView<ItemVariant> view : storage.iterable(tx)) {
            if (!view.isResourceBlank()) {
                ItemVariant var = view.getResource();
                long amount = Math.min(var.getItem().getMaxCount(), Math.min(maxAmount, view.getAmount()));
                long extracted = view.extract(var, amount, tx);
                maxAmount -= extracted;
                if (stack.isEmpty()) {
                    stack = var.toStack((int) extracted);
                } else if (var.matches(stack)) {
                    stack.increment((int) extracted);
                }
                if (maxAmount == 0)
                    break;
            }
        }
        return stack;
    }

    public static ItemStack extractAnyItem(Storage<ItemVariant> storage, long maxAmount) {
        try (Transaction tx = Transaction.openOuter()) {
            ItemStack stack = extractAnyItem(storage, maxAmount, tx);
            tx.commit();
            return stack;
        }
    }

    public static ItemStack simulateExtractAnyItem(Storage<ItemVariant> storage, long maxAmount) {
        try (Transaction tx = Transaction.openOuter()) {
            return extractAnyItem(storage, maxAmount, tx);
        }
    }
}