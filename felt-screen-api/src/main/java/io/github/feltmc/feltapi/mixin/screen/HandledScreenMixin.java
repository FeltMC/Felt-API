package io.github.feltmc.feltapi.mixin.screen;

import io.github.feltmc.feltapi.api.screen.HandledScreenExtension;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HandledScreen.class)
public class HandledScreenMixin implements HandledScreenExtension {

    @Shadow @Nullable protected Slot focusedSlot;

    @Shadow protected int x;

    @Shadow protected int y;

    @Shadow protected int backgroundWidth;

    @Shadow protected int backgroundHeight;

    @Override
    public int getGuiLeft() {
        return this.x;
    }

    @Override
    public int getGuiTop() {
        return this.y;
    }

    @Override
    public int getXSize() {
        return this.backgroundWidth;
    }

    @Override
    public int getYSize() {
        return this.backgroundHeight;
    }

    @Override
    public Slot getSlotUnderMouse() {
        return this.focusedSlot;
    }

    @Unique
    protected int slotColor = -2130706433;

    @Override
    public int getSlotColor(int index) {
        return slotColor;
    }

    @Override
    public void setSlotColor(int index, int color) {
        this.slotColor = color;
    }
}
