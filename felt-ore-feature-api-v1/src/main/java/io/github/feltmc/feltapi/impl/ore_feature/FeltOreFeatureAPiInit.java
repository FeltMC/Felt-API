package io.github.feltmc.feltapi.impl.ore_feature;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreFeature;

import java.util.function.BiConsumer;

public class FeltOreFeatureAPiInit implements ModInitializer {
    @Override
    public void onInitialize() {
        BiomeModifications.create(new Identifier("felt-ore-feature-api-v1", "ore_features")).add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), oreModifier());
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> oreModifier() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            OreFeaturesData.FEATURE_MAP.forEach((s, m) -> {
                boolean hasType = (m.filtered().apply(biomeSelectionContext));
                if (hasType){
                    biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Feature.UNDERGROUND_ORES, m.placedFeature().getKey().get());
                }
            });
        };
    }
}
