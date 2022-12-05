package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import io.github.feltmc.feltapi.api.item.extensions.ItemGroupItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import io.github.feltmc.feltapi.api.item.extensions.StackFoodItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "isIn", at = @At("HEAD"), cancellable = true)
    private void injectGetGroups(ItemGroup group, CallbackInfoReturnable<Boolean> cir){
        if(this instanceof ItemGroupItem extension){
            if (extension.getGroups().stream().anyMatch(g -> g == group)) cir.setReturnValue(true);
        }
    }

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
