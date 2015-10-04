package dmillerw.quirkyworlds.data.loader;

import com.google.common.collect.Maps;
import dmillerw.quirkyworlds.data.struct.Dimension;
import dmillerw.quirkyworlds.data.world.generic.GenericWorldProvider;
import dmillerw.quirkyworlds.util.ExtensionFilter;
import dmillerw.quirkyworlds.util.GsonUtils;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * @author dmillerw
 */
public class DimensionLoader {

    private static Map<Integer, Dimension> activeDimensions = Maps.newHashMap();

    public static boolean isQuirkyWorld(int dimension) {
        return activeDimensions.containsKey(dimension);
    }

    public static Dimension get(int dimension) {
        return activeDimensions.get(dimension);
    }

    public static void initialize(File scanDir) {
        File[] files = scanDir.listFiles(ExtensionFilter.JSON);
        if (files == null || files.length <= 0)
            return;

        for (File file : files) {
            Dimension dimension;
            try {
                dimension = GsonUtils.gson().fromJson(new FileReader(file), Dimension.class);
            } catch (Exception ex) {
                continue;
            }

            if (dimension == null)
                continue;

            System.out.println(GsonUtils.gson().toJson(dimension));

            for (int i : dimension.dimension.numbers) {
                dimension.dimension = null;
                activeDimensions.put(i, dimension);
            }
        }
    }

    public static void registerDimensions() {
        for (int id : activeDimensions.keySet()) {
            if (!DimensionManager.isDimensionRegistered(id)) {
                DimensionManager.registerProviderType(id, GenericWorldProvider.class, false);
                DimensionManager.registerDimension(id, id);
            }
        }
    }
}
