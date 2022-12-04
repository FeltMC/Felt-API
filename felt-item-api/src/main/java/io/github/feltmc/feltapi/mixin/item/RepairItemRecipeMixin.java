package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.ContainerItem;
import io.github.feltmc.feltapi.api.item.extensions.RepairableItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(RepairItemRecipe.class)
public abstract class RepairItemRecipeMixin extends SpecialCraftingRecipe {

    public RepairItemRecipeMixin(Identifier id) {
        super(id);
    }

    @Inject(method = "matches(Lnet/minecraft/inventory/CraftingInventory;Lnet/minecraft/world/World;)Z", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void injectIsRepairable(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> cir, List list, int i, ItemStack itemStack){
        if (itemStack.getItem() instanceof RepairableItem containerItem){
            if (!containerItem.isRepairable(itemStack)){
                cir.setReturnValue(false);
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            Item item = inventory.getStack(i).getItem();
            if (item.hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(item.getRecipeRemainder()));
            }
        }

        return defaultedList;
    }
}
