package net.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.feltmc.feltapi.api.ore_feature.v1.FeltRuleTest;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import java.util.List;
import java.util.function.BiFunction;

public class FeltOreFeatureConfig implements FeatureConfiguration {
    public static final Codec<FeltOreFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.STRING.fieldOf("domain").forGetter(config -> {
            return config.domain;
        }), Codec.STRING.fieldOf("id").forGetter(config -> {
            return config.id;
        }), FeltRuleTest.CODEC.fieldOf("targets").forGetter((config) -> {
            return config.targets;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((config) -> {
            return config.size;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter((config) -> {
            return config.discardOnAirChance;
        })).apply(instance, FeltOreFeatureConfig::new);
    });
    public final FeltRuleTest targets;
    private final String domain, id;
    public final int size;
    public final float discardOnAirChance;

    public FeltOreFeatureConfig(String domain, String id, FeltRuleTest targets, int size, float discardOnAirChance) {
        this.size = size;
        this.targets = targets;
        this.domain = domain;
        this.id = id;
        this.discardOnAirChance = discardOnAirChance;
    }

    public FeltOreFeatureConfig(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> targets, int size, float discardOnAirChance) {
        this(domain, id, new FeltRuleTest(domain, id, targets), size, discardOnAirChance);
    }

    public FeltOreFeatureConfig(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> targets, int size) {
        this(domain, id, new FeltRuleTest(domain, id, targets), size, 0.0F);
    }

    public FeltOreFeatureConfig(String domain, String id, FeltRuleTest targets, int size) {
        this(domain, id, targets, size, 0.0F);
    }

    public FeltOreFeatureConfig(String domain, String id, RuleTest test, BlockState state, int size, float discardOnAirChance) {
        this(domain, id, new FeltRuleTest(domain, id, test, state), size, discardOnAirChance);
    }

    public FeltOreFeatureConfig(String domain, String id, RuleTest test, BlockState state, int size) {
        this(domain, id, new FeltRuleTest(domain, id, test, state), size, 0.0F);
    }

    public List<ResourceKey<Level>> getDimensions(){
        return OreFeaturesData.FEATURE_MAP.get(domain + ":" + id).dimensions();
    }

    public String getDomain() {
        return domain;
    }

    public String getId() {
        return id;
    }
}
