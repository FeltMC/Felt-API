package io.github.feltmc.feltapi.api.screen;

import net.minecraft.screen.slot.Slot;

public interface HandledScreenExtension {
    default int getGuiLeft(){
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default int getGuiTop(){
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default int getXSize(){
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default int getYSize(){
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default Slot getSlotUnderMouse(){
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default int getSlotColor(int index) {
        throw new AssertionError("Mixins Did not Apply!!");
    }

    default void setSlotColor(int index, int color){
        throw new AssertionError("Mixins Did not Apply!!");
    }
}
