package dmillerw.quirkyworlds.data.json.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class BlockSerializer implements JsonSerializer<Block> {

    @Override
    public JsonElement serialize(Block src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(GameData.getBlockRegistry().getNameForObject(src));
    }
}
