package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
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
}
