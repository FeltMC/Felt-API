package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "acceptsForBarter", at = @At("HEAD"), cancellable = true)
    private static void injectAcceptsPiglinItem(ItemStack stack, CallbackInfoReturnable<Boolean> ci){
        if (stack.getItem() instanceof FeltItem item){
            ci.setReturnValue(item.isPiglinCurrency(stack));
        }
    }


    // basically override to avoid iterating the armor list twice
    @Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
    private static void injectIsWearingGold(LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        for(ItemStack itemstack : entity.getArmorItems()) {
            Item item = itemstack.getItem();
            if (item instanceof FeltItem item1){
                boolean bool = item1.makesPiglinsNeutral(itemstack, entity);
                if (bool){
                    cir.setReturnValue(true);
                    return;
                }
            } else if (itemstack.getItem() instanceof ArmorItem armor && armor.getMaterial() == ArmorMaterials.GOLD){
                cir.setReturnValue(true);
                return;
            }
        }
        cir.setReturnValue(false);
    }
}
