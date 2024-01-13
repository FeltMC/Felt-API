package net.feltmc.feltapi.mixin.playeritem.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {
    @ModifyVariable(method = "useItemOn", at = @At(value = "STORE"), index = 8)
    private boolean modifyBL2(boolean value, LocalPlayer player, ClientLevel world, InteractionHand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();
        return value && (!mainStack.getItem().doesSneakBypassUse(mainStack, world, blockPos, player) || !offHandStack.getItem().doesSneakBypassUse(offHandStack, world, blockPos, player));
    }
}
