package io.github.feltmc.feltapi.api.extratransfer.fluid;

import com.google.common.base.Preconditions;
import io.github.feltmc.feltapi.api.extratransfer.item.ItemUtil;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

@SuppressWarnings("UnstableApiUsage")
public class FluidUtil {

    @Nonnull
    public static FluidActionResult tryFillContainer(@Nonnull ItemStack container, Storage<FluidVariant> fluidSource, long maxAmount, @Nullable PlayerEntity player, boolean doFill) {
        ItemStack containerCopy = ItemUtil.copyStackWithSize(container, 1); // do not modify the input
        ContainerItemContext context = ContainerItemContext.withInitial(containerCopy);
        Storage<FluidVariant> containerFluidStorage = context.find(FluidStorage.ITEM);
        if (containerFluidStorage != null){
            // We are acting on a COPY of the stack, so performing changes is acceptable even if we are simulating.
            StoredFluid transfer = tryFluidTransfer(containerFluidStorage, fluidSource, maxAmount, doFill);
            if (transfer.isResourceBlank()) return FluidActionResult.FAILURE;
            if (doFill && player != null) {
                SoundEvent soundevent = FluidVariantAttributes.getEmptySound(transfer.getResource());
                player.world.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            ItemStack resultContainer = context.getItemVariant().toStack();
            return new FluidActionResult(resultContainer);
        }
        return FluidActionResult.FAILURE;
    }
    @Nonnull
    public static FluidActionResult tryEmptyContainer(@Nonnull ItemStack container, Storage<FluidVariant> fluidDestination, long maxAmount, @Nullable PlayerEntity player, boolean doDrain) {
        ItemStack containerCopy = ItemUtil.copyStackWithSize(container, 1); // do not modify the input
        ContainerItemContext context = ContainerItemContext.withInitial(containerCopy);
        Storage<FluidVariant> containerFluidStorage = context.find(FluidStorage.ITEM);
        if (containerFluidStorage != null){
            // We are acting on a COPY of the stack, so performing changes is acceptable even if we are simulating.
            StoredFluid transfer = tryFluidTransfer(fluidDestination, containerFluidStorage, maxAmount, doDrain);
            if (transfer.isResourceBlank()) return FluidActionResult.FAILURE;
            if (doDrain && player != null)
            {
                SoundEvent soundevent = FluidVariantAttributes.getFillSound(transfer.getResource());
                player.world.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            ItemStack resultContainer = context.getItemVariant().toStack();
            return new FluidActionResult(resultContainer);
        }
        return FluidActionResult.FAILURE;
    }

    @Nonnull
    public static FluidActionResult tryEmptyContainerAndStow(@Nonnull ItemStack container, Storage<FluidVariant> fluidDestination, Storage<ItemVariant> inventory, long maxAmount, @Nullable PlayerEntity player, boolean doDrain) {
        if (container.isEmpty()) {
            return FluidActionResult.FAILURE;
        }

        if (player != null && player.getAbilities().creativeMode) {
            FluidActionResult emptiedReal = tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);
            if (emptiedReal.isSuccess()) {
                return new FluidActionResult(container); // creative mode: item does not change
            }
        } else if (container.getCount() == 1) {// don't need to stow anything, just fill and edit the container stack
            FluidActionResult emptiedReal = tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);
            if (emptiedReal.isSuccess()) {
                return emptiedReal;
            }
        } else {
            FluidActionResult emptiedSimulated = tryEmptyContainer(container, fluidDestination, maxAmount, player, false);
            if (emptiedSimulated.isSuccess()) {
                ItemStack remainder = ItemUtil.insertItemStacked(inventory, emptiedSimulated.getResult(), true);
                // check if we can give the itemStack to the inventory
                if (!remainder.isEmpty() || player != null) {
                    FluidActionResult emptiedReal = tryEmptyContainer(container, fluidDestination, maxAmount, player, doDrain);
                    remainder = ItemUtil.insertItemStacked(inventory, emptiedReal.getResult(), !doDrain);

                    // give it to the player or drop it at their feet
                    if (!remainder.isEmpty() && player != null && doDrain) {
                        if (!player.giveItemStack(remainder)){
                            player.dropItem(remainder, true);
                        }
                    }

                    ItemStack containerCopy = container.copy();
                    containerCopy.decrement(1);
                    return new FluidActionResult(containerCopy);
                }
            }
        }
        return FluidActionResult.FAILURE;
    }

    @Nonnull
    public static FluidActionResult tryFillContainerAndStow(@Nonnull ItemStack container, Storage<FluidVariant> fluidSource, Storage<ItemVariant> inventory, long maxAmount, @Nullable PlayerEntity player, boolean doFill) {
        if (container.isEmpty()) {
            return FluidActionResult.FAILURE;
        }

        if (player != null && player.getAbilities().creativeMode) {
            FluidActionResult filledReal = tryFillContainer(container, fluidSource, maxAmount, player, doFill);
            if (filledReal.isSuccess()) {
                return new FluidActionResult(container); // creative mode: item does not change
            }
        } else if (container.getCount() == 1) {// don't need to stow anything, just fill the container stack{
            FluidActionResult filledReal = tryFillContainer(container, fluidSource, maxAmount, player, doFill);
            if (filledReal.isSuccess()) {
                return filledReal;
            }
        } else {
            FluidActionResult filledSimulated = tryFillContainer(container, fluidSource, maxAmount, player, false);
            if (filledSimulated.isSuccess()) {
                // check if we can give the itemStack to the inventory
                ItemStack remainder = ItemUtil.insertItemStacked(inventory, filledSimulated.getResult(), true);
                if (remainder.isEmpty() || player != null) {
                    FluidActionResult filledReal = tryFillContainer(container, fluidSource, maxAmount, player, doFill);
                    remainder = ItemUtil.insertItemStacked(inventory, filledReal.getResult(), !doFill);

                    // give it to the player or drop it at their feet
                    if (!remainder.isEmpty() && player != null && doFill) {
                        ItemUtil.giveItemToPlayer(player, remainder);
                    }

                    ItemStack containerCopy = container.copy();
                    containerCopy.decrement(1);
                    return new FluidActionResult(containerCopy);
                }
            }
        }
        return FluidActionResult.FAILURE;
    }


    public static boolean interactWithFluidHandler(@Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull Storage<FluidVariant> handler) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(hand);
        Preconditions.checkNotNull(handler);
        ItemStack heldItem = player.getStackInHand(hand);
        if (!heldItem.isEmpty()) {
            //FluidActionResult fluidActionResult = tryFillContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);
            FluidActionResult fluidActionResult = tryFillContainer(heldItem, handler, Long.MAX_VALUE, player, true);
            if (!fluidActionResult.isSuccess()) {
                //fluidActionResult = tryEmptyContainerAndStow(heldItem, handler, playerInventory, Integer.MAX_VALUE, player, true);
                fluidActionResult = tryEmptyContainer(heldItem, handler, Long.MAX_VALUE, player, true);
            }

            if (fluidActionResult.isSuccess()) {
                player.setStackInHand(hand, fluidActionResult.getResult());
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Try to extract any FluidStack possible from a Storage.
     * @return the extracted FluidStack, or EMPTY if none.
     */
    public static StoredFluid extractAnyFluid(Storage<FluidVariant> storage, long maxAmount, TransactionContext tx) {
        StoredFluid fluid = StoredFluid.EMPTY;
        if (!storage.supportsExtraction()) return fluid;
        for (StorageView<FluidVariant> view : storage.iterable(tx)) {
            if (!view.isResourceBlank()) {
                FluidVariant var = view.getResource();
                long amount = Math.min(maxAmount, view.getAmount());
                long extracted = view.extract(var, amount, tx);
                maxAmount -= extracted;
                if (fluid.isResourceBlank()) {
                    fluid = StoredFluid.createStoredFluid(var, extracted);
                } else if (fluid.canFill(var)) {
                    fluid.grow(extracted);
                }
                if (maxAmount == 0) {
                    break;
                }
            }
        }
        return fluid;
    }

    /**
     * Try to extract any FluidStack possible from a Storage.
     * @return the extracted FluidStack, or EMPTY if none.
     */
    public static StoredFluid extractAnyFluid(Storage<FluidVariant> storage, long maxAmount) {
        try (Transaction tx = Transaction.openOuter()) {
            StoredFluid fluid = extractAnyFluid(storage, maxAmount, tx);
            tx.commit();
            return fluid;
        }
    }

    /**
     * Try to extract any FluidStack possible from a Storage without affecting the actual storage contents.
     * @return the extracted FluidStack, or EMPTY if none.
     */
    public static StoredFluid simulateExtractAnyFluid(Storage<FluidVariant> storage, long maxAmount) {
        try (Transaction t = Transaction.openOuter()) {
            return extractAnyFluid(storage, maxAmount, t);
        }
    }

    @Nonnull
    public static StoredFluid tryFluidTransfer(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, long maxAmount, boolean doTransfer) {
        StoredFluid drainable = simulateExtractAnyFluid(fluidSource, maxAmount);
        if (!drainable.isResourceBlank()) {
            return tryFluidTransfer_Internal(fluidDestination, fluidSource, drainable.getResource(), drainable.getAmount(), doTransfer);
        }
        return StoredFluid.EMPTY;
    }

    @Nonnull
    public static StoredFluid tryFluidTransfer(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, FluidVariant resource, long maxAmount, boolean doTransfer) {
        long drainable = fluidSource.simulateExtract(resource, maxAmount, null);
        if (drainable > 0) {
            return tryFluidTransfer_Internal(fluidDestination, fluidSource, resource, maxAmount, doTransfer);
        }
        return StoredFluid.EMPTY;
    }

    @Nonnull
    private static StoredFluid tryFluidTransfer_Internal(Storage<FluidVariant> fluidDestination, Storage<FluidVariant> fluidSource, FluidVariant drainable, long amount, boolean doTransfer) {
        long fillableAmount = fluidDestination.simulateInsert(drainable, amount, null);
        if (fillableAmount > 0) {
            if (doTransfer) {
                Transaction transaction = Transaction.openOuter();
                long drained = fluidSource.extract(drainable, fillableAmount, transaction);
                if (drained > 0) {
                    fluidDestination.insert(drainable, drained, transaction);
                    transaction.commit();
                    return StoredFluid.createStoredFluid(drainable, drained);
                }
            }
            else {
                return StoredFluid.createStoredFluid(drainable, fillableAmount);
            }
        }
        return StoredFluid.EMPTY;
    }

    public static long insertFluid(Storage<FluidVariant> storage, FluidVariant fluidVariant, long maxAmount, boolean simulate){
        Transaction transaction = Transaction.openOuter();
        long insert = storage.insert(fluidVariant, maxAmount, transaction);
        if (!simulate) transaction.commit();
        return insert;
    }

    public static long extractFluid(Storage<FluidVariant> storage, FluidVariant fluidVariant, long maxAmount, boolean simulate){
        Transaction transaction = Transaction.openOuter();
        long extract = storage.extract(fluidVariant, maxAmount, transaction);
        if (!simulate) transaction.commit();
        return extract;
    }
}
