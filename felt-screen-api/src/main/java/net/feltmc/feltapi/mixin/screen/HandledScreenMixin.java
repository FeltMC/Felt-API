package net.feltmc.feltapi.mixin.screen;

import net.feltmc.feltapi.api.screen.HandledScreenExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractContainerScreen.class)
public class HandledScreenMixin implements HandledScreenExtension {
    @Shadow @Nullable protected Slot hoveredSlot;
    @Shadow protected int leftPos;
    @Shadow protected int topPos;
    @Shadow protected int imageWidth;
    @Shadow protected int imageHeight;

    @Override
    public int getGuiLeft() {
        return this.leftPos;
    }

    @Override
    public int getGuiTop() {
        return this.topPos;
    }

    @Override
    public int getXSize() {
        return this.imageWidth;
    }

    @Override
    public int getYSize() {
        return this.imageHeight;
    }

    @Override
    public Slot getSlotUnderMouse() {
        return this.hoveredSlot;
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
