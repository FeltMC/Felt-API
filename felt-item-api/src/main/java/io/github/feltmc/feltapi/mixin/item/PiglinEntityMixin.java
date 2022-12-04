package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.PiglinItem;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin {

    @Redirect(method = "equipToOffHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean redirectPiglinItem(ItemStack instance, Item item){
        if (instance.getItem() instanceof PiglinItem item1){
            return item1.isPiglinCurrency(instance);
        }
        return instance.isOf(item);
    }
}
