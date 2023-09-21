package net.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.mobitem.PiglinCurrencyItem;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Piglin.class)
public class PiglinEntityMixin {

    @WrapOperation(method = "equipToOffHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectPiglinItem(ItemStack instance, Item item, Operation<Boolean> operation){
        if (instance.getItem() instanceof PiglinCurrencyItem item1){
            return item1.isPiglinCurrency(instance);
        }
        return operation.call(instance, item);
    }
}
