package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.*;
import dmillerw.quirkyworlds.data.struct.Biome;
import dmillerw.quirkyworlds.util.GsonUtils;
import net.minecraft.world.biome.BiomeGenBase;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class BiomeDeserializer implements JsonDeserializer<Biome> {

    @Override
    public Biome deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Biome biome = new Biome();

        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString()) {
                biome.biome = findBiome(primitive.getAsString());
            } else {
                throw new JsonParseException("Cannot parse Biome from non-string primitive");
            }
        } else if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            biome.biome = findBiome(GsonUtils.get(object, "biome", "plains"));
            biome.decorate = GsonUtils.get(object, "decorate", true);
        } else {
            throw new JsonParseException("Cannot parse biome from type " + typeOfT);
        }

        return biome;
    }

    private BiomeGenBase findBiome(String biome) {
        for (BiomeGenBase b : BiomeGenBase.getBiomeGenArray()) {
            if (b == null)
                continue;

            // First, look for any exact or differing case matches
            if (b.biomeName.equalsIgnoreCase(biome))
                return b;

            // Then, try stripping spaces
            if (b.biomeName.equalsIgnoreCase(biome.replace(" ", "")))
                return b;
            if (b.biomeName.replace(" ", "").equalsIgnoreCase(biome))
                return b;
        }
        //TODO Some proper bloody logging dammit
        System.out.println(biome + " is not a valid biome! Defaulting to plains...");
        return BiomeGenBase.plains;
    }
}
