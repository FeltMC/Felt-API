package net.feltmc.feltapi.mixin.tool;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ShovelItem.class)
public interface ShovelMixin {
    @Accessor("PATH_STATES") static Map<Block, BlockState> getPathed() { throw new AssertionError(); }
}
