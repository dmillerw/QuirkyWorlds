package dmillerw.quirkyworlds.data.json.deserializer;

import com.google.gson.*;
import cpw.mods.fml.common.registry.GameData;
import dmillerw.quirkyworlds.data.struct.BaseBlock;
import net.minecraft.block.Block;

import java.lang.reflect.Type;

/**
 * @author dmillerw
 */
public class BaseBlockDeserializer implements JsonDeserializer<BaseBlock> {

    @Override
    public BaseBlock deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BaseBlock baseBlock = new BaseBlock();

        if (json.isJsonPrimitive()) {
            JsonPrimitive primitive = json.getAsJsonPrimitive();
            if (primitive.isString()) {
                String string = primitive.getAsString();
                if (string.contains("#")) {
                    baseBlock.block = GameData.getBlockRegistry().getObject(string.split("#")[0]);
                    baseBlock.meta = Integer.parseInt(string.split("#")[1]);
                } else {
                    baseBlock.block = GameData.getBlockRegistry().getObject(string);
                }
            } else {
                throw new JsonParseException("Cannot parse BaseBlock form non-string primitive");
            }
        } else if (json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            if (object.has("block"))
                baseBlock.block = context.deserialize(object.get("block"), Block.class);
            if (object.has("meta"))
                baseBlock.meta = object.get("meta").getAsInt();
        } else {
            throw new JsonParseException("Cannot parse BaseBlock from type " + typeOfT);
        }

        return baseBlock;
    }
}
