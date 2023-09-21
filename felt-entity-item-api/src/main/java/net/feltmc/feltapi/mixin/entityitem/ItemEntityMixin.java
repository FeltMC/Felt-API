package net.feltmc.feltapi.mixin.entityitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.entityitem.EntityCustomItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    public ItemEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    //todo event support and finish lifespan references
    @Unique
    public int lifespan = 6000;

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;DDD)V", at = @At("TAIL"))
    private void injectInit(Level world, double x, double y, double z, ItemStack stack, double velocityX, double velocityY, double velocityZ, CallbackInfo ci){
        this.lifespan = !stack.isEmpty() && stack.getItem() instanceof EntityCustomItem feltItem ? feltItem.getEntityLifespan(stack, world) : 6000;
    }

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 6000))
    private int redirectConstant(int constant){
        return this.lifespan;
    }

    @ModifyConstant(method = "setDespawnImmediately", constant = @Constant(intValue = 5999))
    private int redirectSetDespawnImmediately(int constant){
        if (getStack().getItem() instanceof EntityCustomItem customItem) return customItem.getEntityLifespan(getStack(), this.level) - 1;
        return constant;
    }

    @WrapOperation(method = "damage",at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;onItemEntityDestroyed(Lnet/minecraft/entity/ItemEntity;)V"))
    private void wrapOnItemEntityDestroyed(ItemStack instance, ItemEntity entity, Operation<Void> original, DamageSource source, float amount){
        if (instance.getItem() instanceof EntityCustomItem customItem){
            customItem.onItemEntityDestroyed(entity, source);
            return;
        }
        original.call(instance, entity);
    }
}
