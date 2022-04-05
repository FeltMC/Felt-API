package org.quiltmc.qsl.block.extentions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.AbstractBlock;

@Mixin(AbstractBlock.class)
public interface AbstractBlockAccessor extends net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor{

}