package io.github.feltmc.feltapi.mixin.item.client;

import io.github.feltmc.feltapi.api.item.extensions.FeltItem;
import io.github.feltmc.feltapi.api.item.extensions.MiscExtension;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow private ItemStack currentStack;

    @Shadow public abstract TextRenderer getTextRenderer();

    //TODO itemstack.getHighlightTip(itemstack.getHoverName()).equals(lastToolHighlight.getHighlightTip(lastToolHighlight.getHoverName())) in tick, not possible till redirects do locals
    @Redirect(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I"))
    private int injectRenderHeldItemTooltip(TextRenderer instance, StringVisitable text){
        if (currentStack.getItem() instanceof MiscExtension item){
            return this.getTextRenderer().getWidth(item.getHighlightTip(currentStack, (MutableText)text));
        }
        return this.getTextRenderer().getWidth(text);
    }
}
