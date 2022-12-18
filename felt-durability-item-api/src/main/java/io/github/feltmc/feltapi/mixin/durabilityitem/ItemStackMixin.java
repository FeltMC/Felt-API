package io.github.feltmc.feltapi.mixin.durabilityitem;

import io.github.feltmc.feltapi.api.durabilityitem.IsDamageableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Shadow public abstract boolean isDamageable();

    @Shadow private boolean empty;

    @Shadow @Nullable
    public abstract NbtCompound getNbt();

    @Inject(method = "isDamaged", at = @At("HEAD"), cancellable = true)
    private void injectIsDamaged(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object) this;
        if (this.getItem() instanceof IsDamageableItem extension){
            cir.setReturnValue(this.isDamageable() && extension.isDamaged(stack));
        }
    }

    //TODO figure out how to change this to just replace this: this.getItem().getMaxDamage() > 0 with this: extension.isDamageable(stack)
    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void injectIsDamageable(CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack) (Object) this;
        if (this.getItem() instanceof IsDamageableItem extension){
            if (!this.empty && extension.isDamageable(stack)){
                NbtCompound compoundtag = this.getNbt();
                cir.setReturnValue(compoundtag == null || !compoundtag.getBoolean("Unbreakable"));
            } else {
                cir.setReturnValue(false);
            }
        }
    }
}
