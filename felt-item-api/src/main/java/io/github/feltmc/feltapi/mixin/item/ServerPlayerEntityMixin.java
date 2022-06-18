package io.github.feltmc.feltapi.mixin.item;

import com.mojang.authlib.GameProfile;
import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "dropSelectedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getInventory()Lnet/minecraft/entity/player/PlayerInventory;", shift = At.Shift.AFTER), cancellable = true)
    private void injectDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir){
        ItemStack selected = this.getInventory().getMainHandStack();
        if (selected.isEmpty() || (selected.getItem() instanceof MiscExtension item && !item.onDroppedByPlayer(selected,this))) {
            cir.setReturnValue(false);
        }
    }

}
