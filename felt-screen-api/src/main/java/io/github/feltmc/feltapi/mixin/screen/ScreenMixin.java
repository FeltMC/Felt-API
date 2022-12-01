package io.github.feltmc.feltapi.mixin.screen;

import io.github.feltmc.feltapi.api.screen.ScreenExtension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenExtension {
    @Shadow @Nullable protected MinecraftClient client;

    @Override
    public MinecraftClient getMinecraft() {
        return this.client;
    }
}
