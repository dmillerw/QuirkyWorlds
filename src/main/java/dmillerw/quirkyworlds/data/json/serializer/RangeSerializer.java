package dmillerw.quirkyworlds.data.json.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dmillerw.quirkyworlds.data.struct.Range;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class RangeSerializer implements JsonSerializer<Range> {

    @Override
    public JsonElement serialize(Range src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.numbers);
    }
}
