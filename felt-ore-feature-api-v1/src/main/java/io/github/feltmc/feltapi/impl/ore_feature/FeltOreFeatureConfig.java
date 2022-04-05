package io.github.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.Random;
import java.util.function.BiFunction;

public class FeltOreFeatureConfig implements FeatureConfig {
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

    public FeltOreFeatureConfig(String domain, String id, BiFunction<BlockState, Random, BlockState> targets, int size, float discardOnAirChance) {
        this(domain, id, new FeltRuleTest(domain, id, targets), size, discardOnAirChance);
    }

    public FeltOreFeatureConfig(String domain, String id, BiFunction<BlockState, Random, BlockState> targets, int size) {
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
}
