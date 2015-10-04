package dmillerw.quirkyworlds.data.generic;

import dmillerw.quirkyworlds.QuirkyWorlds;
import dmillerw.quirkyworlds.data.DimensionInformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
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

    private DimensionInformation dimensionInformation;
    private long seed;

    private long calculateSeed(long seed, int dim) {
        return dim * 13L + seed;
    }

    private void setSeed(int dim) {
        seed = calculateSeed(worldObj.getSeed(), dim);
    }

    @Override
    protected void registerWorldChunkManager() {
        dimensionInformation = QuirkyWorlds.dimensionInformation;
        worldChunkMgr = new WorldChunkManager(seed, worldObj.getWorldInfo().getTerrainType());
    }

    @Override
    public long getSeed() {
        return seed;
    }

    @Override
    public String getDimensionName() {
        return "QuirkyWorlds/Dimension";
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

    // CLIENT
    @Override
    public IRenderHandler getSkyRenderer() {
        return dimensionInformation.clientInfo.renderStars ? super.getSkyRenderer() : BLANK;
    }

    @Override
    public IRenderHandler getCloudRenderer() {
        return dimensionInformation.clientInfo.renderClouds ? super.getCloudRenderer() : BLANK;
    }

    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
        if (dimensionInformation.clientInfo.skyColor == null)
            return super.getSkyColor(cameraEntity, partialTicks);
        else
            return dimensionInformation.clientInfo.skyColor;
    }

    @Override
    public Vec3 getFogColor(float partialTicks, float idk) {
        if (dimensionInformation.clientInfo.fogColor == null)
            return super.getFogColor(partialTicks, idk);
        else
            return dimensionInformation.clientInfo.fogColor;
    }
}
