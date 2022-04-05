package org.quiltmc.qsl.block.extentions.impl.client;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;

public class BlockRenderLayerMapImpl extends net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl{

	static net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl fabricimpl = new net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl();
	
	public static void put(Block block, RenderLayer layer) {
		fabricimpl.putBlock(block, layer);
	}
	
	public static void put(Fluid fluid, RenderLayer layer) {
		fabricimpl.putFluid(fluid, layer);
	}
	
	
}
