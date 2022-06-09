package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Debug(export = true)
@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbMixin {
    @Shadow private int amount;

    @Shadow protected abstract int getMendingRepairAmount(int experienceAmount);

    @Unique
    ItemStack randomStack;

    @Redirect(method = "repairPlayerGears", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ExperienceOrbEntity;getMendingRepairAmount(I)I"))
    private int redirectMendingRepairAmount(ExperienceOrbEntity instance, int experienceAmount, PlayerEntity player, int amount){
        if (randomStack.getItem() instanceof DamageableItemExtension extension){
           return (int)(amount * extension.getXpRepairRatio(randomStack));
        }
        return getMendingRepairAmount(amount);
    }

    @Inject(method = "repairPlayerGears", at = @At(value = "INVOKE", target = "Ljava/util/Map$Entry;getValue()Ljava/lang/Object;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectRepairPlayerGearsForLocals(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir, Map.Entry<EquipmentSlot, ItemStack> entry){
        this.randomStack = entry.getValue();
    }
}
