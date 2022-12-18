package io.github.feltmc.feltapi.mixin.elytra.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.elytra.ElytraFlightItem;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapElytraCheck(ItemStack stack, Item elytra, Operation<Boolean> original){
        if (stack.getItem() instanceof ElytraFlightItem flightItem){
            return flightItem.canElytraFly(stack, (ClientPlayerEntity)(Object)this);
        }
        return original.call(stack, elytra);
    }
}
