package net.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Piglin.class)
public class PiglinEntityMixin {

    @WrapOperation(method = "holdInOffHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean redirectPiglinItem(ItemStack instance, Item item, Operation<Boolean> operation){
        return instance.getItem().isPiglinCurrency(instance);
    }
}
