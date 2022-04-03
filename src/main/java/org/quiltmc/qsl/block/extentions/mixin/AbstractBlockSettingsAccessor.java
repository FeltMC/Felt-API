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
public interface AbstractBlockSettingsAccessor {
	@Accessor
	Material getMaterial();

	@Accessor
	float getHardness();

	@Accessor
	float getResistance();

	@Accessor
	boolean getCollidable();

	@Accessor
	boolean getRandomTicks();

	@Accessor("luminance")
	ToIntFunction<BlockState> getLuminance();

	//@Accessor
	//Function<BlockState, MapColor> getMapColorProvider();
//Not Working
	
	@Accessor
	BlockSoundGroup getSoundGroup();

	@Accessor
	float getSlipperiness();

	@Accessor
	float getVelocityMultiplier();

	@Accessor
	float getJumpVelocityMultiplier();

	@Accessor
	Identifier getLootTableId();

	@Accessor
	boolean getOpaque();

	@Accessor
	boolean getIsAir();

	@Accessor
	boolean isToolRequired();

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

	@Accessor
	void setMaterial(Material material);

	@Accessor
	void setCollidable(boolean collidable);

	@Accessor
	void setRandomTicks(boolean ticksRandomly);

	//@Accessor
	//void setMapColorProvider(Function<BlockState, MapColor> mapColorProvider);
//Not working
	
	@Accessor
	void setLootTableId(Identifier lootTableId);

	@Accessor
	void setOpaque(boolean opaque);

	@Accessor
	void setIsAir(boolean isAir);

	@Accessor
	void setToolRequired(boolean toolRequired);

	@Accessor
	void setDynamicBounds(boolean dynamicBounds);
	
}