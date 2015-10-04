package dmillerw.quirkyworlds.data.world.terrain;

import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.world.generic.GenericChunkProvider;
import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.util.GsonUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public class LakeTerrainProvider extends GenericTerrainProvider {

    private Block fluid;
    private int waterLevel;
    private boolean bedrockBottom;

    @Override
    public void configure(JsonObject data) {
        fluid = GsonUtils.get(data, "fluid", Block.class, Blocks.water);
        waterLevel = GsonUtils.get(data, "waterLevel", 127);
        bedrockBottom = GsonUtils.get(data, "bedrockBottom", true);
    }

    @Override
    public void setup(World world, GenericChunkProvider provider) {

    }

    @Override
    public void generate(int chunkX, int chunkZ, Block[] blocks, byte[] bytes) {
        int index = 0;
        for (int x=0; x<16; x++) {
            for (int z=0; z<16; z++) {
                int height = 0;
                if (bedrockBottom) {
                    blocks[index] = Blocks.bedrock;
                    bytes[index++] = 0;
                    height++;
                }

                while (height < waterLevel) {
                    blocks[index] = fluid;
                    bytes[index++] = 0;
                    height++;
                }

                while (height < 256) {
                    blocks[index] = null;
                    bytes[index++] = 0;
                    height++;
                }
            }
        }
    }

    @Override
    public void replaceBlocks(int chunkX, int chunkZ, Block[] blocks, byte[] bytes, BiomeGenBase[] biomeGenBases) {

    }
}
