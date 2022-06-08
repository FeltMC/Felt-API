package io.github.feltmc.feltapi.mixin.item.client;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    /*@ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), ordinal = 1)
    private boolean modifyBL2(boolean bl2, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (mainStack.getItem() instanceof DamageableItemExtension extension){
            return bl2 && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        } else if (offHandStack.getItem() instanceof DamageableItemExtension extension){
            return bl2 && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        }
        return bl2;
    }*/

    /*@Inject(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldCancelInteraction()Z", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectInteractBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir, BlockPos blockPos, ItemStack itemStack, boolean bl){
       *//* ItemStack mainStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (mainStack.getItem() instanceof DamageableItemExtension extension){
            bl2 = bl2 && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        } else if (offHandStack.getItem() instanceof DamageableItemExtension extension){
            bl2 = bl2 && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        }*//*
    }*/
}
