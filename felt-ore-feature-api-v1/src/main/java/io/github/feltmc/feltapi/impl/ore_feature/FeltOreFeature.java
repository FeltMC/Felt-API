package io.github.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class FeltOreFeature extends Feature<FeltOreFeatureConfig> {
    public static final FeltOreFeature ORE = new FeltOreFeature();
    public FeltOreFeature(Codec<FeltOreFeatureConfig> configCodec) {
        super(configCodec);
    }

    public FeltOreFeature() {
        this(FeltOreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<FeltOreFeatureConfig> context) {
        return false;
    }
}
