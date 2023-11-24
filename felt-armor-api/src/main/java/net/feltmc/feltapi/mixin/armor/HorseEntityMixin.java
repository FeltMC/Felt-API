package net.feltmc.feltapi.mixin.armor;

import net.feltmc.feltapi.api.armor.HorseArmorTickItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Horse.class)
public abstract class HorseEntityMixin extends AbstractHorse {
    @Shadow public abstract boolean isArmor(ItemStack item);

    protected HorseEntityMixin(EntityType<? extends AbstractHorse> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "playGallopSound", at = @At("TAIL"))
    private void injectPlayWalkSound(SoundType group, CallbackInfo ci){
        ItemStack stack = this.inventory.getItem(1);
        if (isArmor(stack)) stack.getItem().onHorseArmorTick(stack, this.level(), this);
    }
}
