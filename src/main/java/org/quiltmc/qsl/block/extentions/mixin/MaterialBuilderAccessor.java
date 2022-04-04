package org.quiltmc.qsl.block.extentions.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.Material;
import net.minecraft.block.piston.PistonBehavior;

@Mixin(Material.Builder.class)
public interface MaterialBuilderAccessor extends net.fabricmc.fabric.mixin.object.builder.MaterialBuilderAccessor{

	@Accessor
	void setBlocksMovement(boolean blocksMovement);

	@Accessor
	void setBurnable(boolean burnable);

	@Accessor
	void setLiquid(boolean liquid);

	@Accessor
	void setReplaceable(boolean replaceable);

	@Accessor
	void setSolid(boolean solid);

	@Accessor
	void setBlocksLight(boolean blocksLight);


}