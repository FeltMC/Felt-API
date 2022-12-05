package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.ElytraFlightItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import io.github.feltmc.feltapi.api.item.extensions.SweepHitBoxItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{
    @Shadow public abstract ActionResult interact(Entity entity, Hand hand);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(method = "checkFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean wrapElytraCheck(ItemStack stack, Item elytra, Operation<Boolean> original){
        if (stack.getItem() instanceof ElytraFlightItem flightItem){
            return flightItem.canElytraFly(stack, (PlayerEntity)(Object)this);
        }
        return original.call(stack, elytra);
    }

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getNonSpectatingEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private List<LivingEntity> wrapGetEntitiesInBox(World instance, Class<LivingEntity> clazz, Box originalBox, Operation<List<LivingEntity>> original, Entity target){
        if (this.getStackInHand(Hand.MAIN_HAND).getItem() instanceof SweepHitBoxItem extension){
            return instance.getNonSpectatingEntities(clazz, extension.getSweepHitBox(this.getStackInHand(Hand.MAIN_HAND), (PlayerEntity)(Object)this, target));
        }
        return original.call(instance, clazz, originalBox);
    }
}
