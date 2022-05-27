package io.github.feltmc.feltapi.mixin.tool.common;

import io.github.feltmc.feltapi.api.tool.extensions.EnchantmentExtension;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Redirect(method = "calculateRequiredExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int felt_getModifiedExperience(Item instance, Random random, int slotIndex, int bookshelfCount, ItemStack stack){
        return instance instanceof EnchantmentExtension extension ? extension.getItemEnchantability(stack) : instance.getEnchantability();
    }

    @Redirect(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int felt_getModifiedExperience2(Item instance, Random random, ItemStack stack, int level, boolean treasureAllowed){
        return instance instanceof EnchantmentExtension extension ? extension.getItemEnchantability(stack) : instance.getEnchantability();
    }
}
