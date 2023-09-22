package net.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class DimensionalPlacementModifier extends PlacementFilter {
    public static Codec<DimensionalPlacementModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.list(Level.RESOURCE_KEY_CODEC).fieldOf("dimensions").forGetter(m -> m.dimensions)).apply(instance, DimensionalPlacementModifier::new));

    public static final PlacementModifierType<DimensionalPlacementModifier> MODIFIER_TYPE = Registry.register(Registry.PLACEMENT_MODIFIERS, "felt-ore-feature-api-v1:dimensions", DimensionalPlacementModifier::codec);

    public final List<ResourceKey<Level>> dimensions;

    public DimensionalPlacementModifier(List<ResourceKey<Level>> dimensions) {
        this.dimensions = dimensions;
    }

    private static Codec<DimensionalPlacementModifier> codec() {
        return CODEC;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return dimensions.isEmpty() || dimensions.contains(context.getLevel().getLevel().dimension());
    }

    @Override
    public PlacementModifierType<?> type() {
        return MODIFIER_TYPE;
    }
}
