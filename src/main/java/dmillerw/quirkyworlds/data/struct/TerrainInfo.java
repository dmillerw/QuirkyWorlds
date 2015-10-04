package dmillerw.quirkyworlds.data.struct;

import com.google.gson.JsonObject;
import dmillerw.quirkyworlds.data.enums.Terrain;

/**
 * @author dmillerw
 */
public class TerrainInfo {

    public Terrain type = Terrain.VANILLA;
    public JsonObject data = new JsonObject();
}
