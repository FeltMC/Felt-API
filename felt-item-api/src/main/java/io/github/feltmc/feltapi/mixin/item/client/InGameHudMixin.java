package io.github.feltmc.feltapi.mixin.item.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.feltmc.feltapi.api.item.extensions.HighlightTipItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow private ItemStack currentStack;

    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow @Final private MinecraftClient client;
    @WrapOperation(method = "renderHeldItemTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;getWidth(Lnet/minecraft/text/StringVisitable;)I"))
    private int injectRenderHeldItemTooltip(TextRenderer instance, StringVisitable text, Operation<Integer> operation){
        if (currentStack.getItem() instanceof HighlightTipItem item){
            return this.getTextRenderer().getWidth(item.getHighlightTip(currentStack, (MutableText)text));
        }
        return operation.call(instance, text);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Ljava/lang/Object;equals(Ljava/lang/Object;)Z"))
    private boolean wrapEquals(Object name, Object compareName, Operation<Boolean> original){
        ItemStack stack = this.client.player.getInventory().getMainHandStack();
        if (stack.getItem() instanceof HighlightTipItem item1 && this.currentStack.getItem() instanceof HighlightTipItem item2){
            return stack.getName().equals(this.currentStack.getName()) && item1.getHighlightTip(stack, stack.getName()).equals(item2.getHighlightTip(this.currentStack, this.currentStack.getName()));
        }
        return original.call(name, compareName);
    }
}
