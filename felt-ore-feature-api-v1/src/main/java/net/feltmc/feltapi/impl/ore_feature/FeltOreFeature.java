package net.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import net.feltmc.feltapi.api.ore_feature.v1.FeltRuleTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class FeltOreFeature extends Feature<FeltOreFeatureConfig> {
    public static final FeltOreFeature ORE = Registry.register(Registry.FEATURE, "feltapi:ore", new FeltOreFeature());
    public FeltOreFeature(Codec<FeltOreFeatureConfig> configCodec) {
        super(configCodec);
    }

    public FeltOreFeature() {
        this(FeltOreFeatureConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<FeltOreFeatureConfig> context) {
        Random random = context.random();
        BlockPos blockpos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        FeltOreFeatureConfig config = context.config();
        return generate(structureWorldAccess, random, blockpos, config);
    }

    public boolean generate(WorldGenLevel structureWorldAccess, Random random, BlockPos blockpos, FeltOreFeatureConfig config) {

        float f = random.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size / 8.0F;
        int i = Mth.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double minX = (double)blockpos.getX() + Math.sin(f) * (double)f1;
        double maxX = (double)blockpos.getX() - Math.sin(f) * (double)f1;
        double minZ = (double)blockpos.getZ() + Math.cos(f) * (double)f1;
        double maxZ = (double)blockpos.getZ() - Math.cos(f) * (double)f1;
        double minY = blockpos.getY() + random.nextInt(3) - 2;
        double maxY = blockpos.getY() + random.nextInt(3) - 2;
        int x = blockpos.getX() - Mth.ceil(f1) - i;
        int y = blockpos.getY() - 2 - i;
        int z = blockpos.getZ() - Mth.ceil(f1) - i;
        int width = 2 * (Mth.ceil(f1) + i);
        int height = 2 * (2 + i);

        for(int ix = x; ix <= x + width; ++ix) {
            for(int iz = z; iz <= z + width; ++iz) {
                if (y <= structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, ix, iz)) {
                    return this.generateVeinPart(structureWorldAccess, random, config, minX, maxX, minZ, maxZ, minY, maxY, x, y, z, width, height);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(WorldGenLevel world, Random random, FeltOreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int i = 0;
        BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int j = config.size;
        double[] ds = new double[j * 4];

        int k;
        double d;
        double e;
        double g;
        double h;
        for(k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            d = Mth.lerp((double)f, startX, endX);
            e = Mth.lerp((double)f, startY, endY);
            g = Mth.lerp((double)f, startZ, endZ);
            h = random.nextDouble() * (double)j / 16.0D;
            double l = ((double)(Mth.sin(3.1415927F * f) + 1.0F) * h + 1.0D) / 2.0D;
            ds[k * 4] = d;
            ds[k * 4 + 1] = e;
            ds[k * 4 + 2] = g;
            ds[k * 4 + 3] = l;
        }

        int m;
        for(k = 0; k < j - 1; ++k) {
            if (!(ds[k * 4 + 3] <= 0.0D)) {
                for(m = k + 1; m < j; ++m) {
                    if (!(ds[m * 4 + 3] <= 0.0D)) {
                        d = ds[k * 4] - ds[m * 4];
                        e = ds[k * 4 + 1] - ds[m * 4 + 1];
                        g = ds[k * 4 + 2] - ds[m * 4 + 2];
                        h = ds[k * 4 + 3] - ds[m * 4 + 3];
                        if (h * h > d * d + e * e + g * g) {
                            if (h > 0.0D) {
                                ds[m * 4 + 3] = -1.0D;
                            } else {
                                ds[k * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        BulkSectionAccess chunkSectionCache = new BulkSectionAccess(world);

        try {
            for(m = 0; m < j; ++m) {
                d = ds[m * 4 + 3];
                if (!(d < 0.0D)) {
                    e = ds[m * 4];
                    g = ds[m * 4 + 1];
                    h = ds[m * 4 + 2];
                    int n = Math.max(Mth.floor(e - d), x);
                    int o = Math.max(Mth.floor(g - d), y);
                    int p = Math.max(Mth.floor(h - d), z);
                    int q = Math.max(Mth.floor(e + d), n);
                    int r = Math.max(Mth.floor(g + d), o);
                    int s = Math.max(Mth.floor(h + d), p);

                    for(int t = n; t <= q; ++t) {
                        double u = ((double)t + 0.5D - e) / d;
                        if (u * u < 1.0D) {
                            for(int v = o; v <= r; ++v) {
                                double w = ((double)v + 0.5D - g) / d;
                                if (u * u + w * w < 1.0D) {
                                    for(int aa = p; aa <= s; ++aa) {
                                        double ab = ((double)aa + 0.5D - h) / d;
                                        if (u * u + w * w + ab * ab < 1.0D && !world.isOutsideBuildHeight(v)) {
                                            int ac = t - x + (v - y) * horizontalSize + (aa - z) * horizontalSize * verticalSize;
                                            if (!bitSet.get(ac)) {
                                                bitSet.set(ac);
                                                mutable.set(t, v, aa);
                                                if (world.ensureCanWrite(mutable)) {
                                                    LevelChunkSection chunkSection = chunkSectionCache.getSection(mutable);
                                                    if (chunkSection != null) {
                                                        int ad = SectionPos.sectionRelative(t);
                                                        int ae = SectionPos.sectionRelative(v);
                                                        int af = SectionPos.sectionRelative(aa);
                                                        if (placeOre(ad, ae, af, chunkSection, chunkSectionCache, random, mutable, config)) {
                                                            ++i;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable var60) {
            try {
                chunkSectionCache.close();
            } catch (Throwable var59) {
                var60.addSuppressed(var59);
            }

            throw var60;
        }

        chunkSectionCache.close();
        return i > 0;
    }

    public boolean placeOre(int x, int y, int z, LevelChunkSection chunkSection, BulkSectionAccess chunkSectionCache, Random random, BlockPos.MutableBlockPos mutable, FeltOreFeatureConfig config){
        BlockState blockState = chunkSection.getBlockState(x, y, z);

        FeltRuleTest target = config.targets;
        BlockState oreToPlace = target.test(blockState, random);
        if (oreToPlace != null && shouldPlace(chunkSectionCache::getBlockState, random, config, mutable)) {
            chunkSection.setBlockState(x, y, z, oreToPlace, false);
            return true;
        }
        return false;
    }

    public static boolean shouldPlace(Function<BlockPos, BlockState> posToState, Random random, FeltOreFeatureConfig config, BlockPos.MutableBlockPos pos) {
        if (shouldNotDiscard(random, config.discardOnAirChance)) {
            return true;
        } else {
            return !isAdjacentToAir(posToState, pos);
        }
    }

    protected static boolean shouldNotDiscard(Random random, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else if (chance >= 1.0F) {
            return false;
        } else {
            return random.nextFloat() >= chance;
        }
    }
}
