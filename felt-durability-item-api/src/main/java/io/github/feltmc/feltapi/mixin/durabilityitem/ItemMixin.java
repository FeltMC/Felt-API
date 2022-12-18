package io.github.feltmc.feltapi.mixin.durabilityitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.durabilityitem.IsDamageableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @WrapOperation(method = "isEnchantable", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isDamageable()Z"))
    private boolean injectIsEnchantable(Item instance, Operation<Boolean> cir, ItemStack stack){
        if (this instanceof IsDamageableItem extension){
            return extension.isDamageable(stack);
        }
        return cir.call(instance);
    }
}
