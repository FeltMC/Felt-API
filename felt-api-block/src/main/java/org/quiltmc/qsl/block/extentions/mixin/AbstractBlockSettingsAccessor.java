package org.quiltmc.qsl.block.extentions.mixin;

import java.util.function.ToIntFunction;

import com.google.common.base.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor extends net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor{
	
	//Getters
	@Accessor
	AbstractBlock.TypedContextPredicate<EntityType<?>> getAllowsSpawningPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getSolidBlockPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getSuffocationPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getBlockVisionPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getPostProcessPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getEmissiveLightingPredicate();

	@Accessor
	boolean getDynamicBounds();
	
	//Setters
	@Accessor
	void setDynamicBounds(boolean dynamicBounds);
	
	
}