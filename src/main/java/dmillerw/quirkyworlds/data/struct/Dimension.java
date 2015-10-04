package dmillerw.quirkyworlds.data.struct;

import dmillerw.quirkyworlds.data.enums.Time;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;

/**
 * @author dmillerw
 */
public class Dimension {

    public Range dimension = new Range();
    public String name = "GenericDimension";
    public TerrainInfo terrain = new TerrainInfo();
    public GenericFeature[] features = new GenericFeature[0];
    public ClientInfo clientInfo = new ClientInfo();
    public Time time = Time.NORMAL;
}
