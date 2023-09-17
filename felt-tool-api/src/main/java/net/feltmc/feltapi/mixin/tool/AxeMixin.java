package net.feltmc.feltapi.mixin.tool;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface AxeMixin {
    @Accessor("STRIPPED_BLOCKS") static Map<Block, Block> getStripped() { throw new AssertionError(); }
}