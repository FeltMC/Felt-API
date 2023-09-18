package io.github.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.mobitem.EnderMaskItem;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {

    @WrapOperation(method = "isPlayerStaring", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapIsStaring(ItemStack instance, Item pumpkin, Operation<Boolean> original, PlayerEntity player){
        if (instance.getItem() instanceof EnderMaskItem extension){
            return extension.isEnderMask(instance, player, (EndermanEntity)(Object)this);
        }
        return original.call(instance, pumpkin);
    }
}
