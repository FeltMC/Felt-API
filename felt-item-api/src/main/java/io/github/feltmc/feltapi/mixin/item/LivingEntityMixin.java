package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.ElytraFlightItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import io.github.feltmc.feltapi.api.item.extensions.StackFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow protected int roll;

    @Shadow protected ItemStack activeItemStack;

    @WrapOperation(method = "tickFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapElytraCheck(ItemStack stack, Item elytra, Operation<Boolean> original){
        if (stack.getItem() instanceof ElytraFlightItem flightItem){
            return flightItem.canElytraFly(stack, (PlayerEntity)(Object)this) && flightItem.elytraFlightTick(stack, (LivingEntity)(Object)this, this.roll);
        }
        return original.call(stack, elytra);
    }

    @WrapOperation(method = {"shouldSpawnConsumptionEffects", "applyFoodEffects"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getFoodComponent()Lnet/minecraft/item/FoodComponent;"))
    private FoodComponent wrapFoodComponent(Item instance, Operation<FoodComponent> original){
        if (instance instanceof StackFoodItem foodItem) {
            return foodItem.getFoodComponenet(this.activeItemStack, (LivingEntity)(Object)this);
        }
        return original.call(instance);
    }
}
