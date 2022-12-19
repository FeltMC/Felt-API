package io.github.feltmc.feltapi.impl.extratransfer.fluid;

import io.github.feltmc.feltapi.api.extratransfer.fluid.StoredFluid;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

@SuppressWarnings("UnstableApiUsage")
public class StoredFluidImpl implements StoredFluid{

    private FluidVariant variant;
    private long amount;
    public StoredFluidImpl(FluidVariant variant, long amount){
        this.variant = variant;
        this.amount = amount;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        if (resource.isBlank() || maxAmount == 0 || !this.variant.equals(resource)) return 0;
        long drained = Math.min(this.amount, maxAmount);
        transaction.addCloseCallback((t, r) -> {
            if (r.wasCommitted()) {
                this.amount -= drained;
                if (this.amount == 0) this.variant = FluidVariant.blank();
            }
        });
        return drained;
    }

    @Override
    public boolean isResourceBlank() {
        return this.variant.isBlank() || this.amount == 0;
    }

    @Override
    public FluidVariant getResource() {
        return variant;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getCapacity() {
        return amount;
    }

    public void grow(long amount) {
        this.amount += amount;
    }

    public void shrink(long amount){
        this.amount -= amount;
        if (this.amount <= 0) this.variant = FluidVariant.blank();
    }

    public boolean canFill(FluidVariant variant) {
        return variant.equals(this.variant);
    }
}
