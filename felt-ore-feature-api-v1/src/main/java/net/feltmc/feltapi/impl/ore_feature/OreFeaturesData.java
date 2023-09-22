package net.feltmc.feltapi.impl.ore_feature;

import com.google.common.base.Preconditions;
import net.feltmc.feltapi.api.ore_feature.v1.FeltRuleTest;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class OreFeaturesData {
    public static final Map<String, MapWrapper> FEATURE_MAP = new Object2ObjectLinkedOpenHashMap<>();

    public static void createOrePlacedFeature(String domain, String id, FeltRuleTest test, PlacementModifier range, PlacementModifier weight, int size, float discardChance, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filteredBiomes){
        FeltOreFeatureConfig config = new FeltOreFeatureConfig(domain, id, test, size, discardChance);
        Holder<ConfiguredFeature<FeltOreFeatureConfig, ?>> configuredFeature = register(domain, id, new ConfiguredFeature<>(FeltOreFeature.ORE, config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread(), new DimensionalPlacementModifier(dimensions)));
        list.add(range);
        list.add(weight);
        Holder<PlacedFeature> placedFeature = createPlacedFeature(domain, id, configuredFeature, list);
        FEATURE_MAP.put(domain + ":" + id, new MapWrapper(placedFeature, dimensions, filteredBiomes));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String domain, String id, ConfiguredFeature<FC, F> cf) {
        ResourceLocation realId = new ResourceLocation(domain, id);
        Preconditions.checkState(!BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(realId), "Duplicate ID: %s", id);
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, realId.toString(), cf);
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String domain, String id, Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        ResourceLocation realID = new ResourceLocation(domain, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(realID))
            throw new IllegalStateException("Placed Feature ID: \"" + realID.toString() + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placementModifiers)));
    }

    public record MapWrapper(Holder<PlacedFeature> placedFeature, List<ResourceKey<Level>> dimensions, Predicate<BiomeSelectionContext> filtered){}

}
