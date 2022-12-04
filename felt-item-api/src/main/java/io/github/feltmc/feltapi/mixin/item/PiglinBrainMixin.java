package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.PiglinCurrencyItem;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {

    @WrapOperation(method = "acceptsForBarter", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean injectAcceptsPiglinItem(ItemStack stack, Item barteringItem, Operation<Boolean> original){
        if (stack.getItem() instanceof PiglinCurrencyItem item){
            return item.isPiglinCurrency(stack);
        }
        return original.call(barteringItem);
    }
}
