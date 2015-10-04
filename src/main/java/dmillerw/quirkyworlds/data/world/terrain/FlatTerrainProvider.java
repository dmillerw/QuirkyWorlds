package dmillerw.quirkyworlds.data.world.terrain;

import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.struct.BaseBlock;
import dmillerw.quirkyworlds.data.struct.Layer;
import dmillerw.quirkyworlds.data.world.generic.GenericChunkProvider;
import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.util.GsonUtils;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public class FlatTerrainProvider extends GenericTerrainProvider {

    private BaseBlock[] blocks = new BaseBlock[256];

    @Override
    public void configure(JsonObject data) {
        Layer[] layers = GsonUtils.get(data, "layers", Layer[].class, new Layer[0]);
        for (Layer layer : layers) {
            for (int i : layer.range.numbers) {
                blocks[i] = layer.block;
            }
        }
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
                while (height < 256) {
                    BaseBlock block = this.blocks[height];
                    if (block == null) {
                        blocks[index] = null;
                        bytes[index++] = 0;
                    } else {
                        blocks[index] = this.blocks[height].block;
                        bytes[index++] = (byte) this.blocks[height].meta;
                    }
                    height++;
                }
            }
        }
    }

    @Override
    public void replaceBlocks(int chunkX, int chunkZ, Block[] blocks, byte[] bytes, BiomeGenBase[] biomeGenBases) {

    }
}
