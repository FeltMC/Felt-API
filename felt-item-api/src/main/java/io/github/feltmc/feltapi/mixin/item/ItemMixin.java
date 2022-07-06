package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin implements FeltItem {
    @Inject(method = "isIn", at = @At("HEAD"), cancellable = true)
    private void injectGetGroups(ItemGroup group, CallbackInfoReturnable<Boolean> cir){
        if (this.getGroups().stream().anyMatch(g -> g == group)) cir.setReturnValue(true);
    }

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
    private void injectIsEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(this.getItemStackLimit(stack) == 1 && this.isDamageable(stack));
    }
}
