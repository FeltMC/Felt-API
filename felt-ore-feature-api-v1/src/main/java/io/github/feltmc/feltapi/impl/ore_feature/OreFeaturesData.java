package io.github.feltmc.feltapi.impl.ore_feature;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class OreFeaturesData {
    public static final Map<String, MapWrapper> FEATURE_MAP = new Object2ObjectLinkedOpenHashMap<>();

    public static void createOrePlacedFeature(String domain, String id, FeltRuleTest test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){
        FeltOreFeatureConfig config = new FeltOreFeatureConfig(domain, id, test, size, discardChance);
        RegistryEntry<ConfiguredFeature<FeltOreFeatureConfig, ?>> configuredFeature = register(domain, id, new ConfiguredFeature<>(FeltOreFeature.ORE, config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomePlacementModifier.of(), SquarePlacementModifier.of()));
        list.add(range);
        list.add(weight);
        RegistryEntry<PlacedFeature> placedFeature = createPlacedFeature(domain, id, configuredFeature, list);
        FEATURE_MAP.put(domain + ":" + id, new MapWrapper(placedFeature, dimensions, validBiomes, invalidBiomes));
    }

    public static <FC extends FeatureConfig, F extends Feature<FC>> RegistryEntry<ConfiguredFeature<FC, ?>> register(String domain, String id, ConfiguredFeature<FC, F> cf) {
        Identifier realId = new Identifier(domain, id);
        Preconditions.checkState(!BuiltinRegistries.CONFIGURED_FEATURE.getIds().contains(realId), "Duplicate ID: %s", id);
        return BuiltinRegistries.method_40360(BuiltinRegistries.CONFIGURED_FEATURE, realId.toString(), cf);
    }

    public static <FC extends FeatureConfig> RegistryEntry<PlacedFeature> createPlacedFeature(String domain, String id, RegistryEntry<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        Identifier realID = new Identifier(domain, id);
        if (BuiltinRegistries.PLACED_FEATURE.getIds().contains(realID))
            throw new IllegalStateException("Placed Feature ID: \"" + realID.toString() + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(RegistryEntry.upcast(feature), List.copyOf(placementModifiers)));
    }

    public record MapWrapper(RegistryEntry<PlacedFeature> placedFeature, List<RegistryKey<World>> dimensions, List<Biome.Category> validBiomes, List<Biome.Category> invalidBiomes){}
}
