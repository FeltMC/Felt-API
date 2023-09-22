package net.feltmc.feltapi.mixin.mobitem;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.feltmc.feltapi.api.mobitem.SweepHitBoxItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity{

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/EntityGetter;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
    private List<LivingEntity> wrapGetEntitiesInBox(Level instance, Class<LivingEntity> clazz, AABB originalBox, Operation<List<LivingEntity>> original, Entity target){
        if (this.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SweepHitBoxItem extension){
            return instance.getEntitiesOfClass(clazz, extension.getSweepHitBox(this.getItemInHand(InteractionHand.MAIN_HAND), (Player)(Object)this, target));
        }
        return original.call(instance, clazz, originalBox);
    }
}
