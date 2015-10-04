package dmillerw.quirkyworlds.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.json.deserializer.*;
import dmillerw.quirkyworlds.data.json.serializer.BlockSerializer;
import dmillerw.quirkyworlds.data.json.serializer.RangeSerializer;
import dmillerw.quirkyworlds.data.json.serializer.Vec3Serializer;
import dmillerw.quirkyworlds.data.struct.BaseBlock;
import dmillerw.quirkyworlds.data.struct.Biome;
import dmillerw.quirkyworlds.data.struct.Range;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;
import net.minecraft.block.Block;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class GsonUtils {

    private static Gson gson;
    public static Gson gson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            // DESERIALIZER
            builder.registerTypeAdapter(Block.class, new BlockDeserializer());
            builder.registerTypeAdapter(Vec3.class, new Vec3Deserializer());
            builder.registerTypeAdapter(Range.class, new RangeDeserializer());
            builder.registerTypeAdapter(BaseBlock.class, new BaseBlockDeserializer());
            builder.registerTypeAdapter(GenericFeature.class, new GenericFeatureDeserializer());
            builder.registerTypeAdapter(Biome.class, new BiomeDeserializer());

            // SERIALIZER
            builder.registerTypeAdapter(Block.class, new BlockSerializer());
            builder.registerTypeAdapter(Vec3.class, new Vec3Serializer());
            builder.registerTypeAdapter(Range.class, new RangeSerializer());

            gson = builder.create();
        }
        return gson;
    }

    public static <T> T get(JsonObject object, String key, Class<T> clazz, T def) {
        return object.has(key) ? gson().fromJson(object.get(key), clazz) : def;
    }

    public static boolean get(JsonObject object, String key, boolean def) {
        return object.has(key) ? object.get(key).getAsBoolean() : def;
    }

    public static int get(JsonObject object, String key, int def) {
        return object.has(key) ? object.get(key).getAsInt() : def;
    }

    public static String get(JsonObject object, String key, String def) {
        return object.has(key) ? object.get(key).getAsString() : def;
    }
}
