package dmillerw.quirkyworlds.util;

import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author dmillerw
 */
public class BiomeUtils {

    public static int findSimilarBiome(BiomeGenBase[] search, BiomeGenBase base) {
        double distance = Double.MAX_VALUE;
        int match = BiomeGenBase.plains.biomeID; // Default to plains if all else fails

        for (BiomeGenBase biome : search) {
            if (biome != null) {
                if (biome.getClass() == base.getClass()) {
                    return biome.biomeID;
                } else {
                    double d = getBiomeDistance(base, biome);
                    if (d < distance) {
                        distance = d;
                        match = biome.biomeID;
                    }
                }
            }
        }

        return match;
    }

    public static double getBiomeDistance(BiomeGenBase b1, BiomeGenBase b2) {
        float distanceRain = b1.rainfall - b2.rainfall;
        float distanceTemp = b1.temperature - b2.temperature;
        float distanceHeight = b1.heightVariation - b2.heightVariation;
        float distanceRoof = b1.rootHeight - b2.rootHeight;
        return Math.sqrt(
                distanceRain * distanceRain +
                distanceTemp * distanceTemp +
                distanceHeight * distanceHeight +
                distanceRoof * distanceRoof);
    }
}
