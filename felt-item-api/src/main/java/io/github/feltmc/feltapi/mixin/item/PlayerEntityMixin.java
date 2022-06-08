package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.tool.extensions.DamageableItemExtension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract void disableShield(boolean sprinting);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "takeShieldHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;takeShieldHit(Lnet/minecraft/entity/LivingEntity;)V", shift = At.Shift.AFTER), cancellable = true)
    private void injectShieldHit(LivingEntity attacker, CallbackInfo ci){
        ItemStack stack = attacker.getMainHandStack();
        if (stack.getItem() instanceof DamageableItemExtension extension){
            if (extension.canDisableShield(stack, this.activeItemStack, this, attacker)){
                this.disableShield(true);
            }
            ci.cancel();
        }

    }
}
