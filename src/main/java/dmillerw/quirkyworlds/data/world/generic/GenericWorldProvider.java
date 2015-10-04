package dmillerw.quirkyworlds.data.world.generic;

import dmillerw.quirkyworlds.data.loader.DimensionLoader;
import dmillerw.quirkyworlds.data.struct.Dimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.common.DimensionManager;

/**
 * @author dmillerw
 */
public class GenericWorldProvider extends WorldProvider {

    private static final IRenderHandler BLANK = new IRenderHandler() {
        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {

        }
    };

    public static WorldProvider getProviderForDimension(int dim) {
        return DimensionManager.createProviderFor(dim);
    }

    private Dimension dimension;
    private long seed;

    private long calculateSeed(long seed, int dim) {
        return dim * 13L + seed;
    }

    private void setSeed(int dim) {
        seed = calculateSeed(worldObj.getSeed(), dim);
    }

    @Override
    protected void registerWorldChunkManager() {
        dimension = DimensionLoader.get(worldObj.provider.dimensionId);
        worldChunkMgr = new GenericWorldChunkManager(seed, worldObj.getWorldInfo().getTerrainType(), dimension);
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public String getDimensionName() {
        return "QuirkyWorlds:" + dimension.name;
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new GenericChunkProvider(worldObj, seed);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        return super.getBiomeGenForCoords(x, z);
    }

    @Override
    public int getActualHeight() {
        return 256;
    }

    @Override
    public float calculateCelestialAngle(long time, float partial) {
        switch (dimension.time) {
            case DAWN: return 0.75F;
            case MIDDAY: return 0F;
            case DUSK: return 0.25F;
            case MIDNIGHT: return 0.5F;
            default: return super.calculateCelestialAngle(time, partial);
        }
    }

    // CLIENT
    @Override
    public IRenderHandler getSkyRenderer() {
        return dimension.clientInfo.renderSky ? super.getSkyRenderer() : BLANK;
    }

    @Override
    public IRenderHandler getCloudRenderer() {
        return dimension.clientInfo.renderClouds ? super.getCloudRenderer() : BLANK;
    }

    @Override
    public float[] calcSunriseSunsetColors(float p_76560_1_, float p_76560_2_) {
        return dimension.clientInfo.renderSky ? super.calcSunriseSunsetColors(p_76560_1_, p_76560_2_) : new float[] {0, 0, 0, 0};
    }

    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        if (dimension.clientInfo.skyColor == null)
            return super.getSkyColor(cameraEntity, partialTicks);
        else
            return dimension.clientInfo.skyColor;
    }

    @Override
    public Vec3 getFogColor(float partialTicks, float idk) {
        if (dimension.clientInfo.fogColor == null)
            return super.getFogColor(partialTicks, idk);
        else
            return dimension.clientInfo.fogColor;
    }
}
