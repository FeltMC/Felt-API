package net.feltmc.feltapi.mixin.enchanting;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.enchanting.EnchantabilityItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Random;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @WrapOperation(method = "calculateRequiredExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int hookModifiedExperience(Item instance, Operation<Integer> operation, Random random, int slotIndex, int bookshelfCount, ItemStack stack){
        return instance instanceof EnchantabilityItem extension ? extension.getEnchantability(stack) : operation.call(instance);
    }

    @WrapOperation(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getEnchantability()I"))
    private static int hookModifiedExperience2(Item instance, Operation<Integer> operation, Random random, ItemStack stack, int level, boolean treasureAllowed){
        return instance instanceof EnchantabilityItem extension ? extension.getEnchantability(stack) : operation.call(instance);
    }
}