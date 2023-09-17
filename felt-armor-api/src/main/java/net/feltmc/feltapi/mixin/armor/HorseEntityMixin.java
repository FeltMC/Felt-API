package net.feltmc.feltapi.mixin.armor;

import net.feltmc.feltapi.api.armor.HorseArmorTickItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntity.class)
public abstract class HorseEntityMixin extends HorseBaseEntity {
    @Shadow public abstract boolean isHorseArmor(ItemStack item);

    protected HorseEntityMixin(EntityType<? extends HorseBaseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "playWalkSound", at = @At("TAIL"))
    private void injectPlayWalkSound(BlockSoundGroup group, CallbackInfo ci){
        ItemStack stack = this.items.getStack(1);
        if (isHorseArmor(stack) && stack.getItem() instanceof HorseArmorTickItem extension) extension.onHorseArmorTick(stack, this.world, this);
    }
}
