package dmillerw.quirkyworlds.data.world.generic;

import cpw.mods.fml.common.IWorldGenerator;
import dmillerw.quirkyworlds.data.enums.Terrain;
import dmillerw.quirkyworlds.data.loader.DimensionLoader;
import dmillerw.quirkyworlds.data.struct.Dimension;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

/**
 * @author dmillerw
 */
public class GenericWorldGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (!DimensionLoader.isQuirkyWorld(world.provider.dimensionId))
            return;

        Dimension dimension = DimensionLoader.get(world.provider.dimensionId);
        if (chunkX == 0 && chunkZ == 0) {
            if (dimension.terrain.type == Terrain.VOID && dimension.terrain.data.has("generatePlatform")) {
                generateSpawnPlatform(world);
            }
        }
    }

    private void generateSpawnPlatform(World world) {
        // Pretty basic for now. Make better?
        int x = 8;
        int y = 64;
        int z = 8;

        Block block = world.getBlock(x, y, z);
        while (!block.isAir(world, x, y, z)) {
            y++;
            block = world.getBlock(x, y, z);
        }

        world.setBlock(x,     y, z,     Blocks.stone);
        world.setBlock(x - 1, y, z - 1, Blocks.stone);
        world.setBlock(x + 1, y, z + 1, Blocks.stone);
        world.setBlock(x - 1, y, z + 1, Blocks.stone);
        world.setBlock(x + 1, y, z - 1, Blocks.stone);
        world.setBlock(x + 1, y, z,     Blocks.stone);
        world.setBlock(x - 1, y, z,     Blocks.stone);
        world.setBlock(x,     y, z + 1, Blocks.stone);
        world.setBlock(x,     y, z - 1, Blocks.stone);
    }
}
