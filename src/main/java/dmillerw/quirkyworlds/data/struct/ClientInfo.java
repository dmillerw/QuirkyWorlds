package dmillerw.quirkyworlds.data.struct;

import com.google.gson.annotations.SerializedName;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class ClientInfo {

    @SerializedName("sky_color")
    public Vec3 skyColor;
    @SerializedName("fog_color")
    public Vec3 fogColor;
}
