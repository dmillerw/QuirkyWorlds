package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.*;
import net.minecraft.util.Vec3;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class Vec3Deserializer implements JsonDeserializer<Vec3> {

    @Override
    public Vec3 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            JsonArray array = json.getAsJsonArray();
            if (array.size() >= 3) {
                return Vec3.createVectorHelper(array.get(0).getAsDouble(), array.get(1).getAsDouble(), array.get(2).getAsDouble());
            } else {
                throw new JsonParseException("Cannot deserialize vector from array of size " + array.size());
            }
        } else {
            throw new JsonParseException("Cannot deserialize vector from type " + typeOfT);
        }
    }
}
