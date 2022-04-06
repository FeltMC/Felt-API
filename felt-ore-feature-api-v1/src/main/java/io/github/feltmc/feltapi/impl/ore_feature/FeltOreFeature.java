package io.github.feltmc.feltapi.impl.ore_feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkSectionCache;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.BitSet;
import java.util.List;
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
    public boolean generate(FeatureContext<FeltOreFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockpos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        FeltOreFeatureConfig config = context.getConfig();
        if (!config.getDimensions().contains(structureWorldAccess.toServerWorld().getRegistryKey())) return false;
        List<Biome.Category> types = OreFeaturesData.FEATURE_MAP.get(config.getDomain() + ":" + config.getId()).validBiomes();
        List<Biome.Category> invalidTypes = OreFeaturesData.FEATURE_MAP.get(config.getDomain() + ":" + config.getId()).invalidBiomes();
        Biome.Category compare = Biome.getCategory(structureWorldAccess.getBiome(blockpos));
        boolean hasType = (types.isEmpty() || types.contains(compare)) && !invalidTypes.contains(compare);
        if (!hasType){
            return false;
        }
        return generate(structureWorldAccess, random, blockpos, config);
    }

    public boolean generate(StructureWorldAccess structureWorldAccess, Random random, BlockPos blockpos, FeltOreFeatureConfig config) {

        float f = random.nextFloat() * (float)Math.PI;
        float f1 = (float)config.size / 8.0F;
        int i = MathHelper.ceil(((float)config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double minX = (double)blockpos.getX() + Math.sin(f) * (double)f1;
        double maxX = (double)blockpos.getX() - Math.sin(f) * (double)f1;
        double minZ = (double)blockpos.getZ() + Math.cos(f) * (double)f1;
        double maxZ = (double)blockpos.getZ() - Math.cos(f) * (double)f1;
        double minY = blockpos.getY() + random.nextInt(3) - 2;
        double maxY = blockpos.getY() + random.nextInt(3) - 2;
        int x = blockpos.getX() - MathHelper.ceil(f1) - i;
        int y = blockpos.getY() - 2 - i;
        int z = blockpos.getZ() - MathHelper.ceil(f1) - i;
        int width = 2 * (MathHelper.ceil(f1) + i);
        int height = 2 * (2 + i);

        for(int ix = x; ix <= x + width; ++ix) {
            for(int iz = z; iz <= z + width; ++iz) {
                if (y <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, ix, iz)) {
                    return this.generateVeinPart(structureWorldAccess, random, config, minX, maxX, minZ, maxZ, minY, maxY, x, y, z, width, height);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(StructureWorldAccess world, Random random, FeltOreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int i = 0;
        BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j = config.size;
        double[] ds = new double[j * 4];

        int k;
        double d;
        double e;
        double g;
        double h;
        for(k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            d = MathHelper.lerp((double)f, startX, endX);
            e = MathHelper.lerp((double)f, startY, endY);
            g = MathHelper.lerp((double)f, startZ, endZ);
            h = random.nextDouble() * (double)j / 16.0D;
            double l = ((double)(MathHelper.sin(3.1415927F * f) + 1.0F) * h + 1.0D) / 2.0D;
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

        ChunkSectionCache chunkSectionCache = new ChunkSectionCache(world);

        try {
            for(m = 0; m < j; ++m) {
                d = ds[m * 4 + 3];
                if (!(d < 0.0D)) {
                    e = ds[m * 4];
                    g = ds[m * 4 + 1];
                    h = ds[m * 4 + 2];
                    int n = Math.max(MathHelper.floor(e - d), x);
                    int o = Math.max(MathHelper.floor(g - d), y);
                    int p = Math.max(MathHelper.floor(h - d), z);
                    int q = Math.max(MathHelper.floor(e + d), n);
                    int r = Math.max(MathHelper.floor(g + d), o);
                    int s = Math.max(MathHelper.floor(h + d), p);

                    for(int t = n; t <= q; ++t) {
                        double u = ((double)t + 0.5D - e) / d;
                        if (u * u < 1.0D) {
                            for(int v = o; v <= r; ++v) {
                                double w = ((double)v + 0.5D - g) / d;
                                if (u * u + w * w < 1.0D) {
                                    for(int aa = p; aa <= s; ++aa) {
                                        double ab = ((double)aa + 0.5D - h) / d;
                                        if (u * u + w * w + ab * ab < 1.0D && !world.isOutOfHeightLimit(v)) {
                                            int ac = t - x + (v - y) * horizontalSize + (aa - z) * horizontalSize * verticalSize;
                                            if (!bitSet.get(ac)) {
                                                bitSet.set(ac);
                                                mutable.set(t, v, aa);
                                                if (world.isValidForSetBlock(mutable)) {
                                                    ChunkSection chunkSection = chunkSectionCache.getSection(mutable);
                                                    if (chunkSection != null) {
                                                        int ad = ChunkSectionPos.getLocalCoord(t);
                                                        int ae = ChunkSectionPos.getLocalCoord(v);
                                                        int af = ChunkSectionPos.getLocalCoord(aa);
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

    public boolean placeOre(int x, int y, int z, ChunkSection chunkSection, ChunkSectionCache chunkSectionCache, Random random, BlockPos.Mutable mutable, FeltOreFeatureConfig config){
        BlockState blockState = chunkSection.getBlockState(x, y, z);

        FeltRuleTest target = config.targets;
        BlockState oreToPlace = target.test(blockState, random);
        if (oreToPlace != null && shouldPlace(chunkSectionCache::getBlockState, random, config, mutable)) {
            chunkSection.setBlockState(x, y, z, oreToPlace, false);
            return true;
        }
        return false;
    }

    public static boolean shouldPlace(Function<BlockPos, BlockState> posToState, Random random, FeltOreFeatureConfig config, BlockPos.Mutable pos) {
        if (shouldNotDiscard(random, config.discardOnAirChance)) {
            return true;
        } else {
            return !isExposedToAir(posToState, pos);
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
