package net.feltmc.feltapi.mixin.screen;

import net.feltmc.feltapi.api.screen.ScreenExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Screen.class)
public class ScreenMixin implements ScreenExtension {
    @Shadow @Nullable protected Minecraft minecraft;

    @Override
    public Minecraft getMinecraft() {
        return this.minecraft;
    }
}
