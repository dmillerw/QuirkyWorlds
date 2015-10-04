package dmillerw.quirkyworlds.data.generic;

import com.google.gson.JsonObject;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

/**
 * @author dmillerw
 */
public abstract class GenericFeature {

    public abstract void configure(JsonObject data);
    public abstract void generate(IChunkProvider chunkProvider, World world, Random random, int chunkX, int chunkZ, BiomeGenBase biome, boolean villages);
}
