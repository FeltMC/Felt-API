package io.github.feltmc.feltapi.mixin.item.portinglib;

import io.github.fabricators_of_create.porting_lib.util.ToolActions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(method = "getPreferredEquipmentSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private static boolean redirectGetEquipmentSlot(ItemStack instance, Item item){
        return instance.canPerformAction(ToolActions.SHIELD_BLOCK);
    }

    //commented out instead of removed cause porting lib doesn't have an event yet
    /*@Inject(method = "tickActiveItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickItemStackUsage(Lnet/minecraft/item/ItemStack;)V"))
    private void tickItemStackUsage(CallbackInfo ci) {
        if (!this.activeItemStack.isEmpty()) {
            itemUseTimeLeft = LivingEntityUsageTickCallback.TICK.invoker().tick((LivingEntity)(Object)this, activeItemStack, itemUseTimeLeft);
            if (itemUseTimeLeft > 0 && activeItemStack.getItem() instanceof ItemUseExtension item)
                item.onUsingTick(activeItemStack, (LivingEntity) (Object)this, itemUseTimeLeft);
        }

    }*/
}
