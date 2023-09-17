package net.feltmc.feltapi.api.screen;

import net.minecraft.client.MinecraftClient;

public interface ScreenExtension {

    default MinecraftClient getMinecraft(){
        throw new AssertionError("Mixins Did not Apply!!");
    }
}
