package dmillerw.quirkyworlds;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import dmillerw.quirkyworlds.command.CommandBoop;
import dmillerw.quirkyworlds.data.DimensionInformation;
import dmillerw.quirkyworlds.data.enums.Terrain;
import dmillerw.quirkyworlds.data.generic.GenericWorldProvider;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.DimensionManager;

/**
 * @author dmillerw
 */
@Mod(modid = QuirkyWorlds.ID, name = QuirkyWorlds.NAME, version = QuirkyWorlds.VERSION)
public class QuirkyWorlds {

    public static final String ID = "QuirkyWorlds";
    public static final String NAME = ID;
    public static final String VERSION = "%MOD_VERSION%";

    public static DimensionInformation dimensionInformation;

    @Mod.EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        dimensionInformation = new DimensionInformation();
        dimensionInformation.terrain.type = Terrain.VOID;
        dimensionInformation.clientInfo.skyColor = Vec3.createVectorHelper(0, 0, 0);
        dimensionInformation.clientInfo.fogColor = Vec3.createVectorHelper(0, 0, 0);
        dimensionInformation.clientInfo.renderStars = false;
        dimensionInformation.clientInfo.renderClouds = false;

        DimensionManager.registerProviderType(5, GenericWorldProvider.class, false);
        DimensionManager.registerDimension(5, 5);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandBoop());
    }
}
