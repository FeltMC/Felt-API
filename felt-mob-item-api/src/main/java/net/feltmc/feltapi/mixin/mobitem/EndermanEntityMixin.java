package net.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.mobitem.EnderMaskItem;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderMan.class)
public class EndermanEntityMixin {

    @WrapOperation(method = "isPlayerStaring", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapIsStaring(ItemStack instance, Item pumpkin, Operation<Boolean> original, Player player){
        if (instance.getItem() instanceof EnderMaskItem extension){
            return extension.isEnderMask(instance, player, (EnderMan)(Object)this);
        }
        return original.call(instance, pumpkin);
    }
}
