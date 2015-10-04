package dmillerw.quirkyworlds.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dmillerw.quirkyworlds.data.json.deserializer.BlockDeserializer;
import dmillerw.quirkyworlds.data.json.serializer.BlockSerializer;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class GsonUtil {

    private static Gson gson;
    public static Gson gson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            // DESERIALIZER
            builder.registerTypeAdapter(Block.class, new BlockDeserializer());

            // SERIALIZER
            builder.registerTypeAdapter(Block.class, new BlockSerializer());

            gson = builder.create();
        }
        return gson;
    }
}
