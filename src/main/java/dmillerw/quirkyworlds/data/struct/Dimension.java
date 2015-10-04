package dmillerw.quirkyworlds.data.struct;

import com.google.gson.annotations.SerializedName;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;
import dmillerw.quirkyworlds.data.world.generic.GenericWorldProvider;
import net.minecraftforge.common.DimensionManager;

/**
 * @author dmillerw
 */
public class Dimension {

    public Range dimension = new Range();

    public TerrainInfo terrain = new TerrainInfo();
    public GenericFeature[] features = new GenericFeature[0];
    @SerializedName("client_info")
    public ClientInfo clientInfo = new ClientInfo();

    public final void register() {
        for (int i : dimension.numbers) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                DimensionManager.registerProviderType(i, GenericWorldProvider.class, false);
                DimensionManager.registerDimension(i, i);
            }
        }
    }
}
