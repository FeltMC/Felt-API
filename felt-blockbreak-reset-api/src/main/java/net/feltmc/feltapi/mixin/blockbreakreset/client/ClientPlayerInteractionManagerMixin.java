package net.feltmc.feltapi.mixin.blockbreakreset.client;

import net.feltmc.feltapi.api.blockbreakreset.BlockBreakResetItem;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow private ItemStack selectedStack;

    @Shadow private BlockPos currentBreakingPos;
    @Inject(method = "isCurrentlyBreaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectIsCurrentlyBreaking(BlockPos pos, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, boolean bl){
        if (itemStack.getItem() instanceof BlockBreakResetItem extension){
            boolean check = !extension.shouldCauseBlockBreakReset(this.selectedStack, itemStack);
            cir.setReturnValue(pos.equals(this.currentBreakingPos) && check);
        }
    }
}
