package net.feltmc.feltapi.api.ore_feature.v1;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

public class FeltRuleTest {
    public static final Codec<FeltRuleTest> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.STRING.fieldOf("domain").forGetter(config -> {
        return config.domain;
    }), Codec.STRING.fieldOf("id").forGetter(config -> {
        return config.id;
    })).apply(instance, FeltRuleTest::new));
    private final String domain, id;
    private static final Map<String, BiFunction<BlockState, RandomSource, BlockState>> FUNCTION_MAP = new Object2ObjectLinkedOpenHashMap<>();
    private final BiFunction<BlockState, RandomSource, BlockState> blockState;

    private FeltRuleTest(String domain, String id){
        this.domain = domain;
        this.id = id;
        this.blockState = FUNCTION_MAP.get(domain + ":" + id);
    }

    public FeltRuleTest(String domain, String id, BiFunction<BlockState, RandomSource, BlockState> blockState) {
        this.blockState = blockState;
        this.domain = domain;
        this.id = id;
        FUNCTION_MAP.put(domain + ":" + id, blockState);
    }

    public FeltRuleTest(String domain, String id, BlockState match, BlockState ore) {
        this(domain, id, (b, r) -> {
            if (b == match) return ore;
            return null;
        });
    }

    public FeltRuleTest(String domain, String id, RuleTest match, BlockState ore) {
        this(domain, id, (b, r) -> {
            if (match.test(b, r)) return ore;
            return null;
        });
    }

    public BlockState test(BlockState state, RandomSource random) {
        if (blockState == null) return null;
        return this.blockState.apply(state, random);
    }

    protected RuleTestType<?> getType() {
        return RuleTestType.BLOCKSTATE_TEST;
    }
}
