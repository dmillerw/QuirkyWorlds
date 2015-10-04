package dmillerw.quirkyworlds.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * @author dmillerw
 */
public class EntityUtils {

    public static void teleportToDimension(EntityPlayer player, int dimension) {
        int oldDimension = player.worldObj.provider.dimensionId;
        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(dimension);
//        ChunkCoordinates spawn = worldServer.provider.getSpawnPoint();
        //TODO Spawn point?
        player.addExperienceLevel(0);
        MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dimension, new EntityUtils.GenericTeleporter(worldServer, 0, 100, 0));
        if (oldDimension == 1) {
            player.setPositionAndUpdate(0, 100, 0);
            worldServer.spawnEntityInWorld(player);
            worldServer.updateEntityWithOptionalForce(player, false);
        }
    }

    public static class GenericTeleporter extends Teleporter {

        private final WorldServer worldServerInstance;

        private double x;
        private double y;
        private double z;

        public GenericTeleporter(WorldServer world, double x, double y, double z) {
            super(world);
            this.worldServerInstance = world;
            this.x = x;
            this.y = y;
            this.z = z;

        }

        @Override
        public void placeInPortal(Entity pEntity, double p2, double p3, double p4, float p5) {
            this.worldServerInstance.getBlock((int) this.x, (int) this.y, (int) this.z);
            pEntity.setPosition(this.x, this.y, this.z);
            pEntity.motionX = 0.0f;
            pEntity.motionY = 0.0f;
            pEntity.motionZ = 0.0f;
        }
    }
}
