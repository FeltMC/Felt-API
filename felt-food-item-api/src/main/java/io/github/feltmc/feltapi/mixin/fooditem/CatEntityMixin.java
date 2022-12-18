package io.github.feltmc.feltapi.mixin.fooditem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.fooditem.StackFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CatEntity.class)
public class CatEntityMixin {
    @WrapOperation(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent(Item instance, Operation<FoodComponent> original, PlayerEntity player, Hand hand){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(player.getStackInHand(hand), (LivingEntity)(Object)this);
        }
        return original.call(instance);
    }
}
