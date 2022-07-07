package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.DamageableItemExtension;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Shadow public abstract boolean isDamageable();

    @Shadow private boolean empty;

    @Shadow @Nullable public abstract NbtCompound getNbt();

    @Inject(method = "setDamage", at = @At("HEAD"), cancellable = true)
    private void injectSetDamage(int damage, CallbackInfo ci){
        if (this.getItem() instanceof DamageableItemExtension extension){
            extension.setDamage((ItemStack) (Object)this, damage);
            ci.cancel();
        }
    }

    @Inject(method = "isDamaged", at = @At("HEAD"), cancellable = true)
    private void injectIsDamaged(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object) this;
        if (this.getItem() instanceof DamageableItemExtension extension){
            cir.setReturnValue(this.isDamageable() && extension.isDamaged(stack));
        }
    }

    //TODO figure out how to change this to just replace this: this.getItem().getMaxDamage() > 0 with this: extension.isDamageable(stack)
    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void injectIsDamageable(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object) this;
        if (this.getItem() instanceof DamageableItemExtension extension){
            if (!this.empty && extension.isDamageable(stack)){
                NbtCompound compoundtag = this.getNbt();
                cir.setReturnValue(compoundtag == null || !compoundtag.getBoolean("Unbreakable"));
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "getMaxDamage", at = @At(value = "HEAD"), cancellable = true)
    private void injectGetMaxDamage(CallbackInfoReturnable<Integer> cir){
        if (getItem() instanceof DamageableItemExtension extension){
            cir.setReturnValue(extension.getMaxDamage((ItemStack) (Object) this));
        }
    }

    @Inject(method = "getDamage", at = @At(value = "HEAD"), cancellable = true)
    private void injectGetDamage(CallbackInfoReturnable<Integer> cir){
        if (getItem() instanceof DamageableItemExtension extension){
            cir.setReturnValue(extension.getDamage((ItemStack) (Object) this));
        }
    }

}
