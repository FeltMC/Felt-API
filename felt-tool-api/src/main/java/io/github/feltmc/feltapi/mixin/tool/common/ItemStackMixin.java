package io.github.feltmc.feltapi.mixin.tool.common;

import io.github.feltmc.feltapi.api.tool.extensions.DamageableItemExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "getMaxDamage", at = @At(value = "HEAD"), cancellable = true)
    private void injectMaxDamage(CallbackInfoReturnable<Integer> cir){
        if (getItem() instanceof DamageableItemExtension extension){
            cir.setReturnValue(extension.getMaxDamage((ItemStack) (Object) this));
        }
    }

    @Redirect(method = "isDamageable", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getMaxDamage()I"))
    private int hookMaxDamage(Item instance){
        if (instance instanceof DamageableItemExtension extension){
            return extension.getMaxDamage((ItemStack) (Object) this);
        }
        return instance.getMaxDamage();
    }

}
