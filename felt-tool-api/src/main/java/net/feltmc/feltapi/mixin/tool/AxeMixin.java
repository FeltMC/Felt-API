package net.feltmc.feltapi.mixin.tool;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;

@Mixin(AxeItem.class)
public interface AxeMixin {
    @Accessor("STRIPPED_BLOCKS") static Map<Block, Block> getStripped() { throw new AssertionError(); }
}