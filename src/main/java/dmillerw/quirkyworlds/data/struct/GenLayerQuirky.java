package dmillerw.quirkyworlds.data.struct;

import dmillerw.quirkyworlds.data.world.generic.GenericWorldChunkManager;
import dmillerw.quirkyworlds.util.BiomeUtils;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;

/**
 * @author dmillerw
 */
public class GenLayerQuirky extends GenLayer {

    private GenericWorldChunkManager chunkManager;

    public GenLayerQuirky(GenericWorldChunkManager chunkManager, long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
        this.chunkManager = chunkManager;
    }

    @Override
    public int[] getInts(int x, int z, int width, int length) {
        // Vanilla biome layout for the generating chunk
        int[] def = parent.getInts(x, z, width, length);

        if (!chunkManager.dimension.useVanilla()) {
            int[] custom = new int[def.length];
            for (int i=0; i<def.length; i++) {
                if (chunkManager.dimension.isActive(def[i])) {
                    custom[i] = def[i];
                } else {
                    custom[i] = BiomeUtils.findSimilarBiome(chunkManager.dimension.getAvailableBiomes(), BiomeGenBase.getBiome(def[i]));
                }
            }
            return custom;
        } else {
            return def;
        }
    }
}
