package net.feltmc.feltapi.mixin.playeritem;

import com.mojang.authlib.GameProfile;
import net.feltmc.feltapi.api.playeritem.DroppedByPlayerItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerEntityMixin extends Player {

    public ServerPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Inject(method = "drop(Z)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getInventory()Lnet/minecraft/world/entity/player/Inventory;", shift = At.Shift.AFTER), cancellable = true)
    private void injectDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir){
        ItemStack selected = this.getInventory().getSelected();
        if (selected.isEmpty() || (selected.getItem() instanceof DroppedByPlayerItem item && !item.onDroppedByPlayer(selected,this))) {
            cir.setReturnValue(false);
        }
    }
}
