package io.github.feltmc.feltapi.mixin.fooditem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.fooditem.StackFoodItem;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
    @WrapOperation(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent(Item instance, Operation<FoodComponent> original, Item item, ItemStack stack){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(stack, null);
        }
        return original.call(instance);
    }
}
