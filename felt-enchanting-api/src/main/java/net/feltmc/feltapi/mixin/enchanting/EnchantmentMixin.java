package net.feltmc.feltapi.mixin.enchanting;


import net.feltmc.feltapi.api.enchanting.EnchantingItem;
import net.feltmc.feltapi.api.enchanting.EnchantmentExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin implements EnchantmentExtension {
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    public void injectEnchantmentCheck(ItemStack stack, CallbackInfoReturnable<Boolean> callback){
        callback.setReturnValue(this.canApplyAtEnchantingTable(stack));
    }
}
