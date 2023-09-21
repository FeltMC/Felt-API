package net.feltmc.feltapi.mixin.enchanting;


import net.feltmc.feltapi.api.enchanting.EnchantabilityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    public void injectEnchantmentCheck(ItemStack stack, CallbackInfoReturnable<Boolean> callback){
        if (stack.getItem() instanceof EnchantabilityItem extension){
            callback.setReturnValue(extension.canApplyAtEnchantingTable(stack, (Enchantment) (Object)this));
        }
    }
}
