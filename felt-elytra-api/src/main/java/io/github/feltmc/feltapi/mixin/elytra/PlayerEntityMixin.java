package io.github.feltmc.feltapi.mixin.elytra;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.elytra.ElytraFlightItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @WrapOperation(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapElytraCheck(ItemStack stack, Item elytra, Operation<Boolean> original){
        if (stack.getItem() instanceof ElytraFlightItem flightItem){
            return flightItem.canElytraFly(stack, (PlayerEntity)(Object)this);
        }
        return original.call(stack, elytra);
    }
}
