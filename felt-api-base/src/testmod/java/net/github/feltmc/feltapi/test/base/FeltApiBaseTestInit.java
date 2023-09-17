package net.github.feltmc.feltapi.test.base;

import net.feltmc.feltapi.api.ore_feature.v1.OreFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;

public class FeltApiBaseTestInit implements ModInitializer {
    @Override
    public void onInitialize() {
        OreFeatures.createOrePlacedFeature("felt-api-base-testmod", "diamond_block_1", (b, r) -> {
            BlockState ore = null;
            BlockState ore2 = Blocks.EMERALD_ORE.getDefaultState();
            if (b == Blocks.STONE.getDefaultState()){
                ore = Blocks.DIAMOND_ORE.getDefaultState();
            }
            if (b == Blocks.DEEPSLATE.getDefaultState()){
                ore = Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState();
                ore2 = Blocks.DEEPSLATE_EMERALD_ORE.getDefaultState();
            }
            if (ore == null) return null;
            return r.nextBoolean() ? ore : ore2;
        }, -32, 32, 25, 8, 0.0f, World.OVERWORLD);
    }
}
