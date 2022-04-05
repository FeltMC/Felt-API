package org.quiltmc.qsl.block.extentions.api;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricMaterialBuilder;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import org.quiltmc.qsl.block.extentions.mixin.MaterialBuilderAccessor;

public class QuiltMaterialBuilder extends FabricMaterialBuilder{


	
	public QuiltMaterialBuilder(MapColor color) {
	super (color);
	}
	
	public static QuiltMaterialBuilder copyOf(Material material, MapColor color) {
		var builder = new QuiltMaterialBuilder(color);
		@SuppressWarnings("ConstantConditions") var accessor = (MaterialBuilderAccessor) builder;
		accessor.setPistonBehavior(material.getPistonBehavior());
		accessor.setBlocksMovement(material.blocksMovement());
		accessor.setBurnable(material.isBurnable());
		accessor.setLiquid(material.isLiquid());
		accessor.setReplaceable(material.isReplaceable());
		accessor.setSolid(material.isSolid());
		accessor.setBlocksLight(material.blocksLight());
		return builder;
	}

	public static QuiltMaterialBuilder copyOf(Material material) {
		return copyOf(material, material.getColor());
	}

	
	
}
