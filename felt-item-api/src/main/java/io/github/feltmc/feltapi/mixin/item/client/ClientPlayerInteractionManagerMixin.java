package io.github.feltmc.feltapi.mixin.item.client;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @Shadow @Final private ClientPlayNetworkHandler networkHandler;

    @Shadow @Final private MinecraftClient client;

    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), index = 8)
    private boolean modifyBL2(boolean value, ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (mainStack.getItem() instanceof DamageableItemExtension extension){
            return value && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        } else if (offHandStack.getItem() instanceof DamageableItemExtension extension){
            return value && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        }
        return value;
    }

    @Inject(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z", ordinal = 0, shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectOnItemUseFirst(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir, BlockPos blockPos, ItemStack itemStack){
        ItemUsageContext useoncontext = new ItemUsageContext(player, hand, hitResult);
        if (itemStack.getItem() instanceof FeltItem item){
            ActionResult result = item.onItemUseFirst(itemStack, useoncontext);
            if (result != ActionResult.PASS) {
                this.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, hitResult));
                cir.setReturnValue(result);
            }
        }

    }

    @Inject(method = "breakBlock", at = @At("HEAD"), cancellable = true)
    private void injectOnBlockStartBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if (client.player.getMainHandStack().getItem() instanceof FeltItem item && item.onBlockStartBreak(client.player.getMainHandStack(), pos, client.player)){
            cir.setReturnValue(false);
        }
    }
}
