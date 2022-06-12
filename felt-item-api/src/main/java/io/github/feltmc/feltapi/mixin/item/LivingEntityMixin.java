package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.ToolActions;
import io.github.feltmc.feltapi.api.item.extensions.ArmorExtension;
import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private static void injectGetEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> callback){
        if (stack.getItem() instanceof ArmorExtension extension) {
            EquipmentSlot slot = extension.getEquipmentSlot(stack);
            if (slot != null) callback.setReturnValue(slot);
        }
    }

    @Redirect(method = "getPreferredEquipmentSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean redirectGetEquipmentSlot(ItemStack instance, Item item){
        if (instance.getItem() instanceof FeltItem feltItem) {
            return feltItem.canPerformAction(instance, ToolActions.SHIELD_BLOCK);
        }
        return instance.isOf(item);
    }
}
