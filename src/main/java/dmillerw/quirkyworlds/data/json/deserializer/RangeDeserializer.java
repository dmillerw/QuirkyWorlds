package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.*;
import dmillerw.quirkyworlds.data.struct.Range;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class RangeDeserializer implements JsonDeserializer<Range> {

    @Override
    public Range deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Range range = new Range();

        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                range.numbers = new int[] {primitive.getAsInt()};
            } else {
                String string = primitive.getAsString();
                if (string.contains("-")) {
                    String[] split = string.split("-");
                    int left = Integer.parseInt(split[0]);
                    int right = Integer.parseInt(split[1]);
                    if (left < right) {
                        range.numbers = new int[(right - left) + 1];
                        for (int i=left; i<=right; i++) {
                            range.numbers[i - left] = i;
                        }
                    } else {
                        range.numbers = new int[(left - right) + 1];
                        for (int i=right; i<=left; i++) {
                            range.numbers[i - right] = i;
                        }
                    }
                } else {
                    try {
                        range.numbers = new int[] {Integer.parseInt(string)};
                    } catch (NumberFormatException ignore) {
                        throw new JsonParseException("Invalid range string: " + string);
                    }
                }
            }
        } else if (json.isJsonArray()) {
            range.numbers = context.deserialize(json, int[].class);
        }

        return range;
    }
}
