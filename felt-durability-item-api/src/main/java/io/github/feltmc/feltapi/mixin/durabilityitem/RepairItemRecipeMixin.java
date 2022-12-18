package io.github.feltmc.feltapi.mixin.durabilityitem;

import io.github.feltmc.feltapi.api.durabilityitem.RepairableItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {
    @Inject(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectIsRepairable(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir, List list, int i, ItemStack itemStack){
        if (itemStack.getItem() instanceof RepairableItem containerItem){
            if (!containerItem.isRepairable(itemStack)){
                cir.setReturnValue(false);
            }
        }
    }
}
