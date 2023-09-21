package net.feltmc.feltapi.impl.ore_feature;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import java.util.function.BiConsumer;

public class FeltOreFeatureAPiInit implements ModInitializer {
    @Override
    public void onInitialize() {
        BiomeModifications.create(new ResourceLocation("felt-ore-feature-api-v1", "ore_features")).add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), oreModifier());
    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> oreModifier() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            OreFeaturesData.FEATURE_MAP.forEach((s, m) -> {
                boolean hasType = (m.filtered().test(biomeSelectionContext));
                if (hasType){
                    biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, m.placedFeature().unwrapKey().get());
                }
            });
        };
    }
}
