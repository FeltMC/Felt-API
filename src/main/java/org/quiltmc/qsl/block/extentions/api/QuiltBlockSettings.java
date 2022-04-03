package org.quiltmc.qsl.block.extentions.api;

import com.google.common.base.Function;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;




public class QuiltBlockSettings extends FabricBlockSettings{

	protected QuiltBlockSettings(Material material, MapColor color) {
		super(material, color);
		//TODO Auto-generated constructor stub
	}

	//We still need a constructor for Material material + <BlockState, MapColor> mapColorProvider

	QuiltBlockSettings(AbstractBlock.Settings settings) {
	super(settings);	
	
	}

	public QuiltBlockSettings mapColorProvider(Function<BlockState, MapColor> mapColorProvider) {
		((AbstractBlockSettingsAccessor) this).setMapColorProvider(mapColorProvider);
		return this;
	}
	
	
	
	
	
	
	

}
