package net.feltmc.feltapi.mixin.armor;

import net.feltmc.feltapi.api.armor.ArmorEquipItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true)
@Mixin(targets = "net.minecraft.world.inventory.InventoryMenu$1")
public class PlayerScreenHandlerMixin {

    @Shadow @Final private EquipmentSlot val$slot;

    @Shadow @Final private InventoryMenu field_7833;

    @Inject(method = "mayPlace", at = @At("HEAD"))
    private void injectCanEquip(ItemStack stack, CallbackInfoReturnable<Boolean> info){
        info.setReturnValue(stack.getItem().canEquip(stack, val$slot, ((PlayerScreenHandlerAccessor) field_7833).getOwner()));
    }
}
