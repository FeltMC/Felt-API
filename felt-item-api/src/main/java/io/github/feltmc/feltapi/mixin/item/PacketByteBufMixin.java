package io.github.feltmc.feltapi.mixin.item;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import io.github.feltmc.feltapi.api.item.extensions.ShareTagItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin {

    @Shadow public abstract int readableBytes();

    @Shadow public abstract PacketByteBuf writeItemStack(ItemStack stack);

    @ModifyExpressionValue(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isDamageable()Z"))
    private boolean redirectIsDamageable(boolean old, ItemStack stack){
        if (stack.getItem() instanceof IsDamageableItem extension){
            return extension.isDamageable(stack);
        }
        return old;
    }

    @WrapOperation(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getNbt()Lnet/minecraft/nbt/NbtCompound;"))
    public NbtCompound redirectGetTag(ItemStack stack, Operation<NbtCompound> operation){
        if (stack.getItem() instanceof ShareTagItem extension){
            return extension.getShareTag(stack);
        }
        return operation.call(stack);
    }

    @WrapOperation(method = "readItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    public void redirectWriteTag(ItemStack instance, NbtCompound nbt, Operation<Void> operation){
        if (instance.getItem() instanceof ShareTagItem extension){
            extension.readShareTag(instance, nbt);
            return;
        }
        operation.call(instance, nbt);
    }

    //Todo figure this out, for fabricated forge api compat
    /*public PacketByteBuf writeItemStack(ItemStack stack, boolean limitedTag) {
        return writeItemStack(stack);
    }*/
}
