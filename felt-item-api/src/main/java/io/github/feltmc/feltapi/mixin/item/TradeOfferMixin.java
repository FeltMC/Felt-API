package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TradeOffer.class)
public class TradeOfferMixin {

    @WrapOperation(method = "acceptsBuy", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isDamageable()Z"))
    private boolean wrapAcceptsBuy(Item instance, Operation<Boolean> operation, ItemStack given, ItemStack sample){
        if (instance instanceof IsDamageableItem damageableItem){
            return damageableItem.isDamageable(given.copy());
        }
        return operation.call();
    }
}
