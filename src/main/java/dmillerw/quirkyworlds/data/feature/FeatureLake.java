package dmillerw.quirkyworlds.data.feature;

import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.generic.GenericFeature;
import dmillerw.quirkyworlds.util.GsonUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAKE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;

/**
 * @author dmillerw
 */
public class FeatureLake extends GenericFeature {

    private Block[] fluids;

    @Override
    public void configure(JsonObject data) {
        if (data.has("fluids")) {
            fluids = GsonUtil.gson().fromJson(data.getAsJsonArray("fluids"), Block[].class);
        } else {
            fluids = new Block[0];
        }
    }

    @Override
    public void generate(IChunkProvider chunkProvider, World world, Random random, int chunkX, int chunkZ, BiomeGenBase biome, boolean villages) {
        if (fluids.length == 0) {
            // If no custom fluids supplied, generate default water/lava lakes
            if (biome != BiomeGenBase.desert &&
                    biome != BiomeGenBase.desertHills &&
                    !villages &&
                    random.nextInt(4) == 0
                    && TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, villages, LAKE)) {

                int x = (chunkX * 16) + random.nextInt(16) + 8;
                int y = random.nextInt(256);
                int z = (chunkZ * 16) + random.nextInt(16) + 8;
                (new WorldGenLakes(Blocks.water)).generate(world, random, x, y, z);
            }

            if (TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, villages, LAVA) &&
                    !villages &&
                    random.nextInt(8) == 0) {

                int x = (chunkX * 16) + random.nextInt(16) + 8;
                int y = random.nextInt(random.nextInt(248) + 8);
                int z = (chunkZ * 16) + random.nextInt(16) + 8;
                (new WorldGenLakes(Blocks.lava)).generate(world, random, x, y, z);
            }
        } else {
            for (Block block : fluids) {
                if (TerrainGen.populate(chunkProvider, world, random, chunkX, chunkZ, villages, LAKE) &&
                        !villages &&
                        random.nextInt(4) == 0) {

                    int x = (chunkX * 16) + random.nextInt(16) + 8;
                    int y = random.nextInt(256);
                    int z = (chunkZ * 16) + random.nextInt(16) + 8;
                    (new WorldGenLakes(block)).generate(world, random, x, y, z);
                }
            }
        }
    }
}
