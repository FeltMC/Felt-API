package io.github.feltmc.feltapi.mixin.item.nonportinglib;

import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow
    public abstract boolean isDamageable();

    @Shadow public abstract int getMaxCount();

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void injectIsEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        if (this instanceof IsDamageableItem extension){
            cir.setReturnValue(this.getMaxCount() == 1 && extension.isDamageable(stack));
        }

    }
}
