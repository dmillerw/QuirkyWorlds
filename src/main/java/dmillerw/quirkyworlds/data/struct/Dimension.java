package dmillerw.quirkyworlds.data.struct;

import dmillerw.quirkyworlds.data.enums.Time;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public class Dimension {

    public Range dimension = new Range();
    public String name = "GenericDimension";
    public TerrainInfo terrain = new TerrainInfo();
    public GenericFeature[] features = new GenericFeature[0];
    public ClientInfo clientInfo = new ClientInfo();
    public Time time = Time.NORMAL;
    public Biome[] biomes = new Biome[0];

    public void initialize() {
        generateBiomeChecks();
    }

    /* BIOME */
    private BiomeGenBase[] availableBiomes = new BiomeGenBase[0];
    private boolean[] activeBiomes = new boolean[256];
    private boolean[] decoratingBiomes = new boolean[256];

    public boolean useVanilla() {
        return biomes.length == 0;
    }

    public BiomeGenBase[] getAvailableBiomes() {
        return availableBiomes;
    }

    public boolean isActive(int biomeId) {
        if (biomeId > 256 | biomeId < 0)
            return false;

        return activeBiomes[biomeId];
    }

    public boolean canDecorate(int biomeId) {
        if (biomeId > 256 | biomeId < 0)
            return false;

        return decoratingBiomes[biomeId];
    }

    private void generateBiomeChecks() {
        if (!useVanilla()) {
            availableBiomes = new BiomeGenBase[biomes.length];
            for (int i=0; i<biomes.length; i++) {
                Biome biome = biomes[i];
                BiomeGenBase b = biome.biome;

                availableBiomes[i] = b;
                activeBiomes[b.biomeID] = true;
                decoratingBiomes[b.biomeID] = biome.decorate;
            }
        }
    }
}
