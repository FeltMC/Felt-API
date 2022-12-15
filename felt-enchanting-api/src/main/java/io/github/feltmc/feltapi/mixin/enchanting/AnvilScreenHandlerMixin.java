package io.github.feltmc.feltapi.mixin.enchanting;

import io.github.feltmc.feltapi.api.enchanting.BookEnchantableItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Debug(export = true)
@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/Property;set(I)V", ordinal = 5), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectBookCheck(CallbackInfo ci, ItemStack itemStack, int i, int j, int k, ItemStack itemStack2, ItemStack itemStack3, Map map){
        if (itemStack2.getItem() instanceof BookEnchantableItem item){
            if (itemStack3.isOf(Items.ENCHANTED_BOOK) &&
                    !EnchantedBookItem.getEnchantmentNbt(itemStack3).isEmpty() &&
                    !item.isBookEnchantable(itemStack2, itemStack3)) {
                itemStack2 = ItemStack.EMPTY;
            }
        }

    }
}
