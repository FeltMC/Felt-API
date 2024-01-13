package net.feltmc.feltapi.mixin.playeritem;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerInteractionManagerMixin {

    @ModifyVariable(method = "useItemOn", at = @At(value = "STORE"), ordinal = 1)
    private boolean modifyBL2(boolean value, ServerPlayer player, Level world, ItemStack stack, InteractionHand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandItem();
        ItemStack offHandStack = player.getOffhandItem();
        return value && (!mainStack.getItem().doesSneakBypassUse(mainStack, world, blockPos, player) || !offHandStack.getItem().doesSneakBypassUse(offHandStack, world, blockPos, player));
    }
}
