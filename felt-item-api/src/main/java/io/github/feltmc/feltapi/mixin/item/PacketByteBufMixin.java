package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.IsDamageableItem;
import io.github.feltmc.feltapi.api.item.extensions.ShareTagItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin {

    @Shadow public abstract int readableBytes();

    @Shadow public abstract PacketByteBuf writeItemStack(ItemStack stack);

    @Redirect(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isDamageable()Z"))
    private boolean redirectIsDamageable(Item instance, ItemStack stack){
        if (instance instanceof IsDamageableItem extension){
            return extension.isDamageable(stack);
        }
        return instance.isDamageable();
    }

    @Redirect(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getNbt()Lnet/minecraft/nbt/NbtCompound;"))
    public NbtCompound redirectGetTag(ItemStack stack){
        if (stack.getItem() instanceof ShareTagItem extension){
            return extension.getShareTag(stack);
        }
        return stack.getNbt();
    }

    @Redirect(method = "readItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    public void redirectWriteTag(ItemStack instance, NbtCompound nbt){
        if (instance.getItem() instanceof ShareTagItem extension){
            extension.readShareTag(instance, nbt);
            return;
        }
        instance.setNbt(nbt);
    }

    //Todo figure this out, for fabricated forge api compat
    /*public PacketByteBuf writeItemStack(ItemStack stack, boolean limitedTag) {
        return writeItemStack(stack);
    }*/
}
