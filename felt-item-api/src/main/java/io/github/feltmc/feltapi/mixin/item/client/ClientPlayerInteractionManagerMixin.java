package io.github.feltmc.feltapi.mixin.item.client;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), ordinal = 0)
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
}