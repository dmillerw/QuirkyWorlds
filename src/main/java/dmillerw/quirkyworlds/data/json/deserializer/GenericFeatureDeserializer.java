package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.*;
import dmillerw.quirkyworlds.data.enums.Feature;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class GenericFeatureDeserializer implements JsonDeserializer<GenericFeature> {

    @Override
    public GenericFeature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            Feature feature = context.deserialize(object.get("type"), Feature.class);
            if (feature == null)
                throw new JsonParseException("Invalid feature type: " + object.get("type"));

            GenericFeature genericFeature = feature.get();
            genericFeature.configure(object);
            return genericFeature;
        } else {
            throw new JsonParseException("Cannot parse feature from non-object");
        }
    }
}
