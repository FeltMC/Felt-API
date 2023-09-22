package net.feltmc.feltapi.mixin.armor;


import net.feltmc.feltapi.api.armor.ArmorTickItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {
    @Shadow @Final public NonNullList<ItemStack> armor;

    @Shadow @Final public Player player;

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectOnArmorTick(CallbackInfo c){
        armor.forEach(e -> {
            if (e.getItem() instanceof ArmorTickItem extension) extension.onArmorTick(e, player.level(), player);
        });
    }

}
