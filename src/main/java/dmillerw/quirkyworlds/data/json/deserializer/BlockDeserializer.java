package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class BlockDeserializer implements JsonDeserializer<Block> {

    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            String string = json.getAsString();
            if (string.contains(":")) {
                return GameData.getBlockRegistry().getObject(string);
            } else {
                return GameData.getBlockRegistry().getObject("minecraft:" + string);
            }
        } else {
            throw new JsonParseException("Cannot deserialize block from type " + typeOfT);
        }
    }
}
