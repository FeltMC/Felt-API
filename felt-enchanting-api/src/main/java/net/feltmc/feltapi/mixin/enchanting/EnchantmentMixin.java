package net.feltmc.feltapi.mixin.enchanting;


import net.feltmc.feltapi.api.enchanting.EnchantingItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    public void injectEnchantmentCheck(ItemStack stack, CallbackInfoReturnable<Boolean> callback){
        callback.setReturnValue(stack.getItem().canApplyAtEnchantingTable(stack, (Enchantment) (Object)this));
    }
}
