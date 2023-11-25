package net.feltmc.feltapi.mixin.enchanting;

import net.feltmc.feltapi.api.enchanting.BookEnchantableItem;
import net.feltmc.feltapi.api.enchanting.EnchantingItem;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(AnvilMenu.class)
public class AnvilScreenHandlerMixin {
    @Inject(method = "createResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/DataSlot;set(I)V", ordinal = 5), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectBookCheck(CallbackInfo ci, ItemStack itemStack, int i, int j, int k, ItemStack itemStack2, ItemStack itemStack3, Map map){
        if (itemStack3.is(Items.ENCHANTED_BOOK) &&
                !EnchantedBookItem.getEnchantments(itemStack3).isEmpty() &&
                !itemStack2.getItem().isBookEnchantable(itemStack2, itemStack3)) {
            itemStack2.setCount(0);
        }

    }
}
