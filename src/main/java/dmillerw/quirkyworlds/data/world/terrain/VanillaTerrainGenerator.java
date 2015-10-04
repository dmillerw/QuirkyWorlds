package dmillerw.quirkyworlds.data.world.terrain;

import com.google.gson.JsonObject;
import cpw.mods.fml.common.eventhandler.Event;
import dmillerw.quirkyworlds.data.struct.BaseBlock;
import dmillerw.quirkyworlds.data.world.generic.GenericChunkProvider;
import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.util.GsonUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * @author dmillerw
 */
public class VanillaTerrainGenerator extends GenericTerrainProvider {

    private World world;
    protected GenericChunkProvider provider;

    private final double[] noiseField;
    private double[] field_147427_d;
    private double[] field_147428_e;
    private double[] field_147425_f;
    private double[] field_147426_g;

    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;

    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];

    // CONFIGURABLES
    public BaseBlock baseBlock;
    public Block baseLiquid;

    public VanillaTerrainGenerator() {
        this.noiseField = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0F / MathHelper.sqrt_float((j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
    }

    @Override
    public void configure(JsonObject data) {
        if (data.has("base_block")) {
            baseBlock = GsonUtils.gson().fromJson(data.get("base_block"), BaseBlock.class);
        } else {
            baseBlock = null;
        }

        if (data.has("base_liquid")) {
            baseLiquid = GsonUtils.gson().fromJson(data.get("base_liquid"), Block.class);
        } else {
            baseLiquid = null;
        }
    }

    @Override
    public void setup(World world, GenericChunkProvider provider) {
        this.world = world;
        this.provider = provider;

        this.field_147431_j = new NoiseGeneratorOctaves(provider.random, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(provider.random, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(provider.random, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(provider.random, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(provider.random, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(provider.random, 16);

        NoiseGenerator[] noiseGens = {field_147431_j, field_147432_k, field_147429_l, field_147430_m, noiseGen5, noiseGen6};
        noiseGens = TerrainGen.getModdedNoiseGenerators(world, provider.random, noiseGens);
        this.field_147431_j = (NoiseGeneratorOctaves)noiseGens[0];
        this.field_147432_k = (NoiseGeneratorOctaves)noiseGens[1];
        this.field_147429_l = (NoiseGeneratorOctaves)noiseGens[2];
        this.field_147430_m = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
    }

    private void func_147423_a(int chunkX4, int chunkY4, int chunkZ4, BiomeGenBase[] biomesForGeneration) {
        double d0 = 684.412D;
        double d1 = 684.412D;
        double d2 = 512.0D;
        double d3 = 512.0D;
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, chunkX4, chunkZ4, 5, 5, 200.0D, 200.0D, 0.5D);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, chunkX4, chunkY4, chunkZ4, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        boolean flag1 = false;
        boolean flag = false;
        int l = 0;
        int i1 = 0;
        double d4 = 8.5D;

        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        BiomeGenBase biomegenbase1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        /*if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0F) {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }*/

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight) {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d12 = this.field_147426_g[i1] / 8000.0D;

                if (d12 < 0.0D) {
                    d12 = -d12 * 0.3D;
                }

                d12 = d12 * 3.0D - 2.0D;

                if (d12 < 0.0D) {
                    d12 /= 2.0D;

                    if (d12 < -1.0D) {
                        d12 = -1.0D;
                    }

                    d12 /= 1.4D;
                    d12 /= 2.0D;
                } else {
                    if (d12 > 1.0D) {
                        d12 = 1.0D;
                    }

                    d12 /= 8.0D;
                }

                ++i1;
                double d13 = (double) f1;
                double d14 = (double) f;
                d13 += d12 * 0.2D;
                d13 = d13 * 8.5D / 8.0D;
                double d5 = 8.5D + d13 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = ((double) j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D) {
                        d6 *= 4.0D;
                    }

                    double d7 = this.field_147428_e[l] / 512.0D;
                    double d8 = this.field_147425_f[l] / 512.0D;
                    double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29) {
                        double d11 = (double) ((float) (j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.noiseField[l] = d10;
                    ++l;
                }
            }
        }
    }

    @Override
    public void generate(int chunkX, int chunkZ, Block[] aBlock, byte[] meta, BiomeGenBase[] biomesForGeneration) {
        func_147423_a(chunkX * 4, 0, chunkZ * 4, biomesForGeneration);

        byte waterLevel = 63;
        byte b0 = 63;

        for (int k = 0; k < 4; ++k) {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2) {
                    double d0 = 0.125D;
                    double d1 = this.noiseField[k1 + k2];
                    double d2 = this.noiseField[l1 + k2];
                    double d3 = this.noiseField[i2 + k2];
                    double d4 = this.noiseField[j2 + k2];
                    double d5 = (this.noiseField[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.noiseField[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.noiseField[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.noiseField[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3) {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3) {
                                if ((d15 += d16) > 0.0D) {
                                    aBlock[j3 += short1] = Blocks.stone;
                                } else if (k2 * 8 + l2 < b0) {
                                    aBlock[j3 += short1] = Blocks.water;
                                } else {
                                    aBlock[j3 += short1] = null;
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    @Override
    public void replaceBlocks(int chunkX, int chunkZ, Block[] aBlock, byte[] abyte, BiomeGenBase[] biomeGenBases) {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(provider, chunkX, chunkZ, aBlock, abyte, biomeGenBases, world);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) {
            return;
        }

        double d0 = 0.03125D;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (chunkX * 16), (chunkZ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                BiomeGenBase biomegenbase = biomeGenBases[l + k * 16];
                biomegenbase.genTerrainBlocks(world, provider.random, aBlock, abyte, chunkX * 16 + k, chunkZ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }
}
