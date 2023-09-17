package net.feltmc.feltapi.mixin.playeritem;

import net.feltmc.feltapi.api.playeritem.SneakBypassUseItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @ModifyVariable(method = "interactBlock", at = @At(value = "STORE"), ordinal = 1)
    private boolean modifyBL2(boolean value, ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult){
        BlockPos blockPos = hitResult.getBlockPos();
        ItemStack mainStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();
        if (mainStack.getItem() instanceof SneakBypassUseItem extension)
            return value && !extension.doesSneakBypassUse(mainStack, world, blockPos, player);
        else if (offHandStack.getItem() instanceof SneakBypassUseItem extension)
            return value && !extension.doesSneakBypassUse(offHandStack, world, blockPos, player);
        return value;
    }
}
