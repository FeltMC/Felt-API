package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.PiglinCurrencyItem;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "acceptsForBarter", at = @At("HEAD"), cancellable = true)
    private static void injectAcceptsPiglinItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci){
        if (stack.getItem() instanceof PiglinCurrencyItem item){
            ci.setReturnValue(item.isPiglinCurrency(stack));
        }
    }
}
