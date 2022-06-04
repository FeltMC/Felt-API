package io.github.feltmc.feltapi.mixin.item;

import io.github.feltmc.feltapi.api.item.extensions.ItemTagExtension;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PacketByteBuf.class)
public class PacketByteBufMixin {

    @Redirect(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getNbt()Lnet/minecraft/nbt/NbtCompound;"))
    public NbtCompound redirectGetTag(ItemStack stack){
        if (stack.getItem() instanceof ItemTagExtension extension){
            return extension.getShareTag(stack);
        }
        return stack.getNbt();
    }

    @Redirect(method = "readItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V"))
    public void redirectWriteTag(ItemStack instance, NbtCompound nbt){
        if (instance.getItem() instanceof ItemTagExtension extension){
            extension.readShareTag(instance, nbt);
            return;
        }
        instance.setNbt(nbt);
    }
}
