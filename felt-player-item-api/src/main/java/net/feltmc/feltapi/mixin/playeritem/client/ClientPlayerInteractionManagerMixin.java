package net.feltmc.feltapi.mixin.playeritem.client;

import net.feltmc.feltapi.api.playeritem.SneakBypassUseItem;
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
    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), index = 8)
    private boolean modifyBL2(boolean value, LocalPlayer player, ClientLevel world, InteractionHand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();
        if (mainStack.getItem() instanceof SneakBypassUseItem extension)
            return value && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        else if (offHandStack.getItem() instanceof SneakBypassUseItem extension)
            return value && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        return value;
    }
}
