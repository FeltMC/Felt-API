package io.github.feltmc.feltapi.mixin.fooditem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.fooditem.StackFoodItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class ItemMixin {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent(Item instance, Operation<FoodComponent> original, World world, PlayerEntity player, Hand hand){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(player.getStackInHand(hand), null);
        }
        return original.call(instance);
    }

    @WrapOperation(method = "getMaxUseTime", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent2(Item instance, Operation<FoodComponent> original, ItemStack stack){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(stack, null);
        }
        return original.call(instance);
    }
}
