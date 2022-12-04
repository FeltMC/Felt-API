package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.PiglinCurrencyItem;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin {

    @WrapOperation(method = "equipToOffHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectPiglinItem(ItemStack instance, Item item, Operation<Boolean> operation){
        if (instance.getItem() instanceof PiglinCurrencyItem item1){
            return item1.isPiglinCurrency(instance);
        }
        return operation.call(item);
    }
}
