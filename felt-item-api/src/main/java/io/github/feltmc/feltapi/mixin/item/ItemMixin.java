package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import io.github.feltmc.feltapi.api.item.extensions.ItemGroupItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
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
}
