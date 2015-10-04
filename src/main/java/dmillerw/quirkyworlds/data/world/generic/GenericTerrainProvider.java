package dmillerw.quirkyworlds.data.world.generic;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public abstract class GenericTerrainProvider {

    public abstract void configure(JsonObject data);
    public abstract void setup(World world, GenericChunkProvider provider);
    public abstract void generate(int chunkX, int chunkZ, Block[] blocks, byte[] bytes);
    public abstract void replaceBlocks(int chunkX, int chunkZ, Block[] blocks, byte[] bytes, BiomeGenBase[] biomeGenBases);
}
