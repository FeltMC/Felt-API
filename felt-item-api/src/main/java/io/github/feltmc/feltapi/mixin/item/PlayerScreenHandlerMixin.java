package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.ArmorEquipItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public class PlayerScreenHandlerMixin {

    @Shadow @Final private EquipmentSlot field_7834;

    @Shadow @Final private PlayerScreenHandler field_7833;

    @Inject(method = "canInsert", at = @At("HEAD"))
    private void injectCanEquip(ItemStack stack, CallbackInfoReturnable<Boolean> info){
        if (stack.getItem() instanceof ArmorEquipItem extension){
            info.setReturnValue(extension.canEquip(stack, field_7834, ((PlayerScreenHandlerAccessor)field_7833).getOwner()));
        }
    }
}
