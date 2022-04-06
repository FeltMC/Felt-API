package io.github.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.AbstractConditionalPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.List;
import java.util.Random;

public class DimensionalPlacementModifier extends AbstractConditionalPlacementModifier {
    public static Codec<DimensionalPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(World.CODEC).fieldOf("dimensions").forGetter(m -> m.dimensions)).apply(instance, DimensionalPlacementModifier::new));

    public static final PlacementModifierType<DimensionalPlacementModifier> MODIFIER_TYPE = Registry.register(Registry.PLACEMENT_MODIFIER_TYPE, "felt-ore-feature-api-v1:dimensions", DimensionalPlacementModifier::codec);

    public final List<RegistryKey<World>> dimensions;

    public DimensionalPlacementModifier(List<RegistryKey<World>> dimensions) {
        this.dimensions = dimensions;
    }

    private static Codec<DimensionalPlacementModifier> codec() {
        return CODEC;
    }

    @Override
    protected boolean shouldPlace(FeaturePlacementContext context, Random random, BlockPos pos) {
        return dimensions.contains(context.getWorld().toServerWorld().getRegistryKey());
    }

    @Override
    public PlacementModifierType<?> getType() {
        return MODIFIER_TYPE;
    }
}
