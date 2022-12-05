package io.github.feltmc.feltapi.mixin.item.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.BlockBreakResetItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import io.github.feltmc.feltapi.api.item.extensions.SneakBypassUseItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Debug(export = true)
@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow @Final private ClientPlayNetworkHandler networkHandler;

    @Shadow @Final private MinecraftClient client;

    @Shadow private ItemStack selectedStack;

    @Shadow private BlockPos currentBreakingPos;

    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), index = 8)
    private boolean modifyBL2(boolean value, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (mainStack.getItem() instanceof SneakBypassUseItem extension){
            return value && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        } else if (offHandStack.getItem() instanceof SneakBypassUseItem extension){
            return value && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        }
        return value;
    }

    @Inject(method = "isCurrentlyBreaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectIsCurrentlyBreaking(BlockPos pos, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, boolean bl){
        if (itemStack.getItem() instanceof BlockBreakResetItem extension){
            boolean check = !extension.shouldCauseBlockBreakReset(this.selectedStack, itemStack);
            cir.setReturnValue(pos.equals(this.currentBreakingPos) && check);
        }
    }
}
