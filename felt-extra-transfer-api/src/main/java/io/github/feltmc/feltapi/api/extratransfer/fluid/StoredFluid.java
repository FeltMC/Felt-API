package io.github.feltmc.feltapi.api.extratransfer.fluid;

import io.github.feltmc.feltapi.impl.extratransfer.fluid.StoredFluidImpl;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;

@SuppressWarnings("UnstableApiUsage")
public interface StoredFluid extends StorageView<FluidVariant> {
    static StoredFluid createStoredFluid(FluidVariant fluidVariant, long amount){
        return new StoredFluidImpl(fluidVariant, amount);
    }

    StoredFluid EMPTY = new StoredFluidImpl(FluidVariant.blank(), 0);

    void grow(long amount);

    void shrink(long amount);

    boolean canFill(FluidVariant variant);
}
