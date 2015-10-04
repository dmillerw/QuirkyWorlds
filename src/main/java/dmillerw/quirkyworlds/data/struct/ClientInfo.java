package dmillerw.quirkyworlds.data.struct;

import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class ClientInfo {

    public Vec3 skyColor;
    public Vec3 fogColor;

    //TODO More specific control
    public boolean renderSky = true;
    public boolean renderClouds = true;
}
