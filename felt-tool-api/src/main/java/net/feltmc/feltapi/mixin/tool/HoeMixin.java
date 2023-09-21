package net.feltmc.feltapi.mixin.tool;

import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

@Mixin(HoeItem.class)
public interface HoeMixin {
    @Accessor("TILLING_ACTIONS") static Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> getTilled() { throw new AssertionError(); }
}
