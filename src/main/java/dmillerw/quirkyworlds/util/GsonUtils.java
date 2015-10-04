package dmillerw.quirkyworlds.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmillerw.quirkyworlds.data.json.deserializer.BlockDeserializer;
import dmillerw.quirkyworlds.data.json.deserializer.RangeDeserializer;
import dmillerw.quirkyworlds.data.json.deserializer.Vec3Deserializer;
import dmillerw.quirkyworlds.data.json.serializer.BlockSerializer;
import dmillerw.quirkyworlds.data.json.serializer.RangeSerializer;
import dmillerw.quirkyworlds.data.json.serializer.Vec3Serializer;
import dmillerw.quirkyworlds.data.struct.Range;
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

            // SERIALIZER
            builder.registerTypeAdapter(Block.class, new BlockSerializer());
            builder.registerTypeAdapter(Vec3.class, new Vec3Serializer());
            builder.registerTypeAdapter(Range.class, new RangeSerializer());

            gson = builder.create();
        }
        return gson;
    }
}
