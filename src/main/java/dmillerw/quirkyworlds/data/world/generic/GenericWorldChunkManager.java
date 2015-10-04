package dmillerw.quirkyworlds.data.world.generic;

import dmillerw.quirkyworlds.data.struct.Dimension;
import dmillerw.quirkyworlds.data.struct.GenLayerQuirky;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;

/**
 * @author dmillerw
 */
public class GenericWorldChunkManager extends WorldChunkManager {

    public static Dimension forcedDimension;

    public Dimension dimension;

    public GenericWorldChunkManager(long seed, WorldType type, Dimension dimension) {
        super(seed, type);
        this.dimension = dimension;
    }

    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        if (dimension == null)
            dimension = forcedDimension;

        GenLayer[] layers = super.getModdedBiomeGenerators(worldType, seed, original);
        GenLayer filteredLayer = new GenLayerQuirky(this, seed, layers[0]);
        GenLayerVoronoiZoom zoom = new GenLayerVoronoiZoom(10L, filteredLayer);
        zoom.initWorldGenSeed(seed);
        return new GenLayer[] {filteredLayer, zoom, filteredLayer};
    }
}
