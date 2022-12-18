package io.github.feltmc.feltapi.mixin.durabilityitem;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.feltmc.feltapi.api.durabilityitem.IsDamageableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PacketByteBuf.class)
public class PacketByteBufMixin {
    @ModifyExpressionValue(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isDamageable()Z"))
    private boolean redirectIsDamageable(boolean old, ItemStack stack){
        if (stack.getItem() instanceof IsDamageableItem extension){
            return extension.isDamageable(stack);
        }
        return old;
    }
}
