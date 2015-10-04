package dmillerw.quirkyworlds.data.world.generic;

import dmillerw.quirkyworlds.QuirkyWorlds;
import dmillerw.quirkyworlds.data.struct.Dimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.ANIMALS;

/**
 * @author dmillerw
 */
public class GenericChunkProvider implements IChunkProvider {

    public Random random;
    public long seed;

    private World world;

    public Dimension dimension;

    public BiomeGenBase[] biomesForGeneration;

    private GenericTerrainProvider terrainProvider;

    public GenericChunkProvider(World world, long seed) {
        this.world = world;
        this.seed = seed;
        this.random = new Random((seed + 516) * 314);
        this.dimension = QuirkyWorlds.dimension;
        this.terrainProvider = dimension.terrain.type.get();
        this.terrainProvider.configure(dimension.terrain.data);
        this.terrainProvider.setup(world, this);
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return this.provideChunk(chunkX, chunkZ);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkZ) {
        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L + 123456);

        Block[] blocks = new Block[65536];
        byte[] bytes = new byte[65536];

        biomesForGeneration = world.getWorldChunkManager().getBiomesForGeneration(
                biomesForGeneration,
                chunkX * 16,
                chunkZ * 16,
                16,
                16);

        terrainProvider.generate(chunkX, chunkZ, blocks, bytes);

        biomesForGeneration = world.getWorldChunkManager().loadBlockGeneratorData(
                biomesForGeneration,
                chunkX * 16,
                chunkZ * 16,
                16,
                16
        );

        terrainProvider.replaceBlocks(chunkX, chunkZ, blocks, bytes, biomesForGeneration);

        Chunk chunk = new Chunk(world, blocks, bytes, chunkX, chunkZ);
        byte[] biomes = chunk.getBiomeArray();

        for (int i=0; i<biomes.length; i++) {
            biomes[i] = (byte) biomesForGeneration[i].biomeID;
        }

        chunk.generateSkylightMap();

        return chunk;
    }

    @Override
    public boolean chunkExists(int p_73149_1_, int p_73149_2_) {
        return true;
    }

    @Override
    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        BlockFalling.fallInstantly = true;

        int x = chunkX * 16;
        int z = chunkZ * 16;

        BiomeGenBase biome = world.getBiomeGenForCoords(x + 16, z + 16);

        random.setSeed(world.getSeed());

        long r1 = random.nextLong() / 2L * 2L + 1L;
        long r2 = random.nextLong() / 2L * 2L + 1L;
        random.setSeed(chunkX * r1 + chunkZ * r2 ^ world.getSeed());

        boolean villages = false;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, world, random, chunkX, chunkZ, villages));

        //TODO Structures, etc

        for (GenericFeature feature : dimension.features) {
            feature.generate(provider, world, random, chunkX, chunkZ, biome, villages);
        }

        boolean doGen = false;
        // Dungeon here yo

        biome.decorate(world, random, x, z);
        if (TerrainGen.populate(provider, world, random, chunkX, chunkZ, villages, ANIMALS)) {
            SpawnerAnimals.performWorldGenSpawning(world, biome, x + 8, z + 8, 16, 16, random);
        }
        x += 8;
        z += 8;

        // Ice ice bby
        for (int k1 = 0; doGen && k1 < 16; ++k1) {
            for (int l1 = 0; l1 < 16; ++l1) {
                int i2 = world.getPrecipitationHeight(x + k1, z + l1);

                if (world.isBlockFreezable(k1 + x, i2 - 1, l1 + z)) {
                    world.setBlock(k1 + x, i2 - 1, l1 + z, Blocks.ice, 0, 2);
                }

                if (world.func_147478_e(k1 + x, i2, l1 + z, true)) {
                    world.setBlock(k1 + x, i2, l1 + z, Blocks.snow_layer, 0, 2);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, world, random, chunkX, chunkZ, villages));

        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    @Override
    public void saveExtraData() {

    }

    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "QuirkyWorlds/Random";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType creatureType, int x, int y, int z) {
        return world.getBiomeGenForCoords(x, z).getSpawnableList(creatureType); // Configurable
    }

    @Override
    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int p_82695_1_, int p_82695_2_) {

    }
}
