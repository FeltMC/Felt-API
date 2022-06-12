package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow @Final protected ServerPlayerEntity player;

    @Shadow protected ServerWorld world;

    @Shadow private GameMode gameMode;

    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), ordinal = 1)
    private boolean modifyBL2(boolean value, ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult){
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
    private void injectOnItemUseFirst(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir, BlockPos blockPos, BlockState blockState){
        ItemUsageContext useoncontext = new ItemUsageContext(player, hand, hitResult);
        if (stack.getItem() instanceof FeltItem item){
            ActionResult result = item.onItemUseFirst(stack, useoncontext);
            if (result != ActionResult.PASS) {
                cir.setReturnValue(result);
            }
        }

    }

    @Redirect(method = "tryBreakBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;isBlockBreakingRestricted(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/GameMode;)Z"))
    private boolean injectOnBlockStartBreak(ServerPlayerEntity instance, World world, BlockPos pos, GameMode gameMode){
        if (this.player.getMainHandStack().getItem() instanceof FeltItem item && item.onBlockStartBreak(this.player.getMainHandStack(), pos, player)){
            return true;
        }
        return this.player.isBlockBreakingRestricted(this.world, pos, this.gameMode);
    }
}
