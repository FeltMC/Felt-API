package net.feltmc.feltapi.api.ore_feature.v1;

import net.feltmc.feltapi.impl.ore_feature.OreFeaturesData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class OreFeatures {
    public static void createFilteredOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, dimensions, filtered::test);
    }

    public static void createOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, List.of(dimensions), context -> true);
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BlockState test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, dimensions, filtered::test);
    }

    public static void createOrePlacedFeature(String domain, String id, BlockState test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, List.of(dimensions), context -> true);
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, dimensions, filtered::test);
    }

    public static void createOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, List.of(dimensions), context -> true);
    }

    public static void createTrapezoidFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacement.triangle(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, dimensions, filtered::test);
    }

    public static void createTrapezoidOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacement.triangle(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, List.of(dimensions), context -> true);
    }

    public static void createTrapezoidFilteredOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.triangle(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, dimensions, filtered::test);
    }

    public static void createTrapezoidOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacement.triangle(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)), CountPlacement.of(weight), size, discardChance, List.of(dimensions), context -> true);
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), range, weight, size, discardChance, dimensions, filtered);
    }

    public static void createOrePlacedFeature(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, ResourceKey<Level>... dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), range, weight, size, discardChance, List.of(dimensions), context -> true);
    }
}
