package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.ToolActions;
import io.github.feltmc.feltapi.api.item.event.LivingEntityUsageTickCallback;
import io.github.feltmc.feltapi.api.item.extensions.ArmorExtension;
import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import io.github.feltmc.feltapi.api.item.extensions.ItemUseExtension;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow public abstract Hand getActiveHand();

    @Shadow protected ItemStack activeItemStack;

    @Shadow protected int itemUseTimeLeft;

    @Shadow protected abstract void tickItemStackUsage(ItemStack stack);

    @Shadow public abstract void clearActiveItem();

    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private static void injectGetEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> callback){
        if (stack.getItem() instanceof ArmorExtension extension) {
            EquipmentSlot slot = extension.getEquipmentSlot(stack);
            if (slot != null) callback.setReturnValue(slot);
        }
    }

    @Redirect(method = "getPreferredEquipmentSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private static boolean redirectGetEquipmentSlot(ItemStack instance, Item item){
        if (instance.getItem() instanceof MiscExtension feltItem) {
            return feltItem.canPerformAction(instance, ToolActions.SHIELD_BLOCK);
        }
        return instance.isOf(item);
    }

    @Inject(method = "tickActiveItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;areItemsEqual(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", shift = At.Shift.BEFORE))
    private void injectTickItemStackUsage(CallbackInfo ci){
        ItemStack itemStack = this.getStackInHand(this.getActiveHand());
        if (canContinueUsing(this.activeItemStack, itemStack)) this.activeItemStack = itemStack;
    }

    @Inject(method = "tickActiveItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickItemStackUsage(Lnet/minecraft/item/ItemStack;)V"))
    private void tickItemStackUsage(CallbackInfo ci) {
        if (!this.activeItemStack.isEmpty()) {
            itemUseTimeLeft = LivingEntityUsageTickCallback.TICK.invoker().tick((LivingEntity)(Object)this, activeItemStack, itemUseTimeLeft);
            if (itemUseTimeLeft > 0 && activeItemStack.getItem() instanceof ItemUseExtension item)
                item.onUsingTick(activeItemStack, (LivingEntity) (Object)this, itemUseTimeLeft);
        }

    }

    private static boolean canContinueUsing(@Nonnull ItemStack from, @Nonnull ItemStack to)
    {
        if (!from.isEmpty() && !to.isEmpty())
        {
            return from.getItem() instanceof ItemUseExtension item && item.canContinueUsing(from, to);
        }
        return false;
    }
}
