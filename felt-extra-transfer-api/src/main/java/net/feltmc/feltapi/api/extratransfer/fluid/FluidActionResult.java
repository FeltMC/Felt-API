package net.feltmc.feltapi.api.extratransfer.fluid;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class FluidActionResult {
    public static final FluidActionResult FAILURE = new FluidActionResult(false, ItemStack.EMPTY);

    public final boolean success;
    @Nonnull
    public final ItemStack result;

    public FluidActionResult(@Nonnull ItemStack result)
    {
        this(true, result);
    }

    private FluidActionResult(boolean success, @Nonnull ItemStack result)
    {
        this.success = success;
        this.result = result;
    }

    public boolean isSuccess()
    {
        return success;
    }

    @Nonnull
    public ItemStack getResult()
    {
        return result;
    }
}
