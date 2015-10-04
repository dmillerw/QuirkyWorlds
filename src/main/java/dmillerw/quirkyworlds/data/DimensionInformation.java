package dmillerw.quirkyworlds.data;

import com.google.gson.annotations.SerializedName;
import dmillerw.quirkyworlds.data.generic.GenericFeature;
import dmillerw.quirkyworlds.data.struct.ClientInfo;
import dmillerw.quirkyworlds.data.struct.TerrainInfo;

/**
 * @author dmillerw
 */
public class DimensionInformation {

    public int dimension;

    public TerrainInfo terrain = new TerrainInfo();
    public GenericFeature[] features = new GenericFeature[0];
    @SerializedName("client_info")
    public ClientInfo clientInfo = new ClientInfo();
}
