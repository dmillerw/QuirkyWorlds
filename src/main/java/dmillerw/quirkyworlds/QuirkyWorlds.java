package dmillerw.quirkyworlds;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.quirkyworlds.command.CommandDump;
import dmillerw.quirkyworlds.command.CommandTPX;
import dmillerw.quirkyworlds.data.loader.DimensionLoader;
import dmillerw.quirkyworlds.data.world.generic.GenericWorldGenerator;

import java.io.File;

/**
 * @author dmillerw
 */
@Mod(modid = QuirkyWorlds.ID, name = QuirkyWorlds.NAME, version = QuirkyWorlds.VERSION)
public class QuirkyWorlds {

    public static final String ID = "QuirkyWorlds";
    public static final String NAME = ID;
    public static final String VERSION = "%MOD_VERSION%";

    public static File dimensionDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        dimensionDir = new File(event.getModConfigurationDirectory(), "QuirkyWorlds/dimensions");
        if (!dimensionDir.exists())
            dimensionDir.mkdirs();

        DimensionLoader.initialize(dimensionDir);

        GameRegistry.registerWorldGenerator(new GenericWorldGenerator(), 1000);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        DimensionLoader.registerDimensions();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTPX());
        event.registerServerCommand(new CommandDump());
    }
}
