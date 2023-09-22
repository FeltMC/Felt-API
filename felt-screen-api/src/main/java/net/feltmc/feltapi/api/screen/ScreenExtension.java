package net.feltmc.feltapi.api.screen;

import net.minecraft.client.Minecraft;

public interface ScreenExtension {

    default Minecraft getMinecraft(){
        throw new AssertionError("Mixins Did not Apply!!");
    }
}
