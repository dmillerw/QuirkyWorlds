package dmillerw.quirkyworlds.data.world.terrain;

import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.world.generic.GenericChunkProvider;
import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public class VoidTerrainProvider extends GenericTerrainProvider {

    @Override
    public void configure(JsonObject data) {

    }

    @Override
    public void setup(World world, GenericChunkProvider provider) {

    }

    @Override
    public void generate(int chunkX, int chunkZ, Block[] blocks, byte[] bytes, BiomeGenBase[] biomesForGeneration) {
        for (int i = 0 ; i < 65536 ; i++) {
            blocks[i] = null;
        }
    }

    @Override
    public void replaceBlocks(int chunkX, int chunkZ, Block[] blocks, byte[] bytes, BiomeGenBase[] biomeGenBases) {
        for (int i = 0 ; i < 65536 ; i++) {
            bytes[i] = 0;
        }
    }
}
