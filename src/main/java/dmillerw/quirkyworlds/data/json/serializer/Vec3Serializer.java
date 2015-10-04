package dmillerw.quirkyworlds.data.json.serializer;

import com.google.gson.*;
import net.minecraft.util.Vec3;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class Vec3Serializer implements JsonSerializer<Vec3> {

    @Override
    public JsonElement serialize(Vec3 src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray array = new JsonArray();
        array.add(new JsonPrimitive(src.xCoord));
        array.add(new JsonPrimitive(src.yCoord));
        array.add(new JsonPrimitive(src.zCoord));
        return array;
    }
}
