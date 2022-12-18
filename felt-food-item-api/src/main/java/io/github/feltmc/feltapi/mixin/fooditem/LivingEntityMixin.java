package io.github.feltmc.feltapi.mixin.fooditem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.fooditem.StackFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow protected ItemStack activeItemStack;

    @WrapOperation(method = {"shouldSpawnConsumptionEffects", "applyFoodEffects"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent(Item instance, Operation<FoodComponent> original){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(this.activeItemStack, (LivingEntity)(Object)this);
        }
        return original.call(instance);
    }
}
