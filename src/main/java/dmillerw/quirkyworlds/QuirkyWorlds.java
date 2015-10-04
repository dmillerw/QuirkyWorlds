package dmillerw.quirkyworlds;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import dmillerw.quirkyworlds.command.CommandTPX;
import dmillerw.quirkyworlds.data.enums.Terrain;
import dmillerw.quirkyworlds.data.struct.Dimension;
import dmillerw.quirkyworlds.data.world.generic.GenericWorldProvider;
import net.minecraftforge.common.DimensionManager;

/**
 * @author dmillerw
 */
@Mod(modid = QuirkyWorlds.ID, name = QuirkyWorlds.NAME, version = QuirkyWorlds.VERSION)
public class QuirkyWorlds {

    public static final String ID = "QuirkyWorlds";
    public static final String NAME = ID;
    public static final String VERSION = "%MOD_VERSION%";

    public static Dimension dimension;

    @Mod.EventHandler
    public void postInit(FMLPreInitializationEvent event) {
        dimension = new Dimension();
        dimension.dimension.numbers = new int[5];
        dimension.terrain.type = Terrain.VANILLA;
        dimension.terrain.data = new JsonObject();
        JsonObject base = new JsonObject();
        base.add("block", new JsonPrimitive("dirt"));
        dimension.terrain.data.add("base_block", base);

        DimensionManager.registerProviderType(11, GenericWorldProvider.class, false);
        DimensionManager.registerDimension(11, 11);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandTPX());
    }
}
