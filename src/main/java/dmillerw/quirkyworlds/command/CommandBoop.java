package dmillerw.quirkyworlds.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * @author dmillerw
 */
public class CommandBoop extends CommandBase {

    @Override
    public String getCommandName() {
        return "boop";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        teleportToDimension((EntityPlayer) p_71515_1_, 5, 0, 10, 0);
    }

    public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
        int oldDimension = player.worldObj.provider.dimensionId;
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(dimension);
        player.addExperienceLevel(0);
        MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dimension,
                new RfToolsTeleporter(worldServer, x, y, z));
        if (oldDimension == 1) {
            // For some reason teleporting out of the end does weird things.
            player.setPositionAndUpdate(x, y, z);
            worldServer.spawnEntityInWorld(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }

    public static class RfToolsTeleporter extends Teleporter {
        private final WorldServer worldServerInstance;

        private double x;
        private double y;
        private double z;


        public RfToolsTeleporter(WorldServer world, double x, double y, double z) {
            super(world);
            this.worldServerInstance = world;
            this.x = x;
            this.y = y;
            this.z = z;

        }

        @Override
        public void placeInPortal(Entity pEntity, double p2, double p3, double p4, float p5) {
            this.worldServerInstance.getBlock((int) this.x, (int) this.y, (int) this.z);   //dummy load to maybe gen chunk

            pEntity.setPosition(this.x, this.y, this.z);
            pEntity.motionX = 0.0f;
            pEntity.motionY = 0.0f;
            pEntity.motionZ = 0.0f;
        }

    }
}
