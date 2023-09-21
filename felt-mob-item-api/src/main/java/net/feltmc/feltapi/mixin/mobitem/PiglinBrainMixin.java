package net.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.mobitem.PiglinCurrencyItem;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(PiglinAi.class)
public abstract class PiglinBrainMixin {

    @WrapOperation(method = "acceptsForBarter", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean injectAcceptsPiglinItem(ItemStack stack, Item barteringItem, Operation<Boolean> original){
        if (stack.getItem() instanceof PiglinCurrencyItem item){
            return item.isPiglinCurrency(stack);
        }
        return original.call(stack, barteringItem);
    }
}
