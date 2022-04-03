package org.quiltmc.qsl.block.extentions.api.client;

import org.quiltmc.qsl.block.extentions.impl.client.BlockRenderLayerMapImpl;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;

public class BlockRenderLayerMap {

	public static void put(RenderLayer layer, Block... blocks) {
		for (var block : blocks) {
			BlockRenderLayerMapImpl.put(block, layer);
		}
	}

	/**
	 * Sets the render layer of the specified fluids.
	 *
	 * @param layer  new render layer
	 * @param fluids target fluids
	 */
	public static void put(RenderLayer layer, Fluid... fluids) {
		for (var fluid : fluids) {
			BlockRenderLayerMapImpl.put(fluid, layer);
		}
	}
	
	
	
}
