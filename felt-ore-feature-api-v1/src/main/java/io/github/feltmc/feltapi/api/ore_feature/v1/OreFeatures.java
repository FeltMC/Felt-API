package io.github.feltmc.feltapi.api.ore_feature.v1;

import io.github.feltmc.feltapi.impl.ore_feature.FeltRuleTest;
import io.github.feltmc.feltapi.impl.ore_feature.OreFeaturesData;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class OreFeatures {
    public static void createFilteredOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, List.of(), List.of());
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BlockState test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createOrePlacedFeature(String domain, String id, BlockState test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, List.of(), List.of());
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacementModifier.uniform(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, List.of(), List.of());
    }

    public static void createTrapezoidFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createTrapezoidOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, List.of(), List.of());
    }

    public static void createTrapezoidFilteredOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createTrapezoidOrePlacedFeature(String domain, String id, RuleTest test, BlockState ore, int minY, int maxY, int weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test, ore), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)), CountPlacementModifier.of(weight), size, discardChance, dimensions, List.of(), List.of());
    }

    public static void createFilteredOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), range, weight, size, discardChance, dimensions, validBiomes, invalidBiomes);
    }

    public static void createOrePlacedFeature(String domain, String id, BiFunction<BlockState, Random, BlockState> test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, List<RegistryKey<World>> dimensions){
        OreFeaturesData.createOrePlacedFeature(domain, id, new FeltRuleTest(domain, id, test), range, weight, size, discardChance, dimensions, List.of(), List.of());
    }
}