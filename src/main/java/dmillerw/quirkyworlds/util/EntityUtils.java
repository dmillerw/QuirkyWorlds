package dmillerw.quirkyworlds.util;

import net.minecraft.block.Block;
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

    public static boolean teleportToDimension(EntityPlayer player, int dimension) {
        int oldDimension = player.worldObj.provider.dimensionId;

        MinecraftServer server = MinecraftServer.getServer();

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
        WorldServer world = server.worldServerForDimension(dimension);

        if (world == null)
            return false;

        player.addExperienceLevel(0);

        server.getConfigurationManager().transferPlayerToDimension(
                entityPlayerMP,
                dimension,
                new EntityUtils.GenericTeleporter(world, 8, 64, 8));

        if (oldDimension == 1) {
            player.setPositionAndUpdate(8, 64, 8);
            world.spawnEntityInWorld(player);
            world.updateEntityWithOptionalForce(player, false);
        }

        return true;
    }

    public static class GenericTeleporter extends Teleporter {

        private final WorldServer world;

        private int x;
        private int y;
        private int z;

        public GenericTeleporter(WorldServer world, int x, int y, int z) {
            super(world);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;

        }

        @Override
        public void placeInPortal(Entity entity, double p2, double p3, double p4, float p5) {
            Block block = world.getBlock(x, y, z);
            if (block.isAir(world, x, y, z)) {
                while(block.isAir(world, x, y, z)) {
                    y--;
                    block = world.getBlock(x, y, z);
                }
            } else {
                while (!block.isAir(world, x, y, z)) {
                    y++;
                    block = world.getBlock(x, y, z);
                }
            }

            entity.setPosition(x + 0.5, y + 1, z + 0.5);
            entity.motionX = 0.0f;
            entity.motionY = 0.0f;
            entity.motionZ = 0.0f;
        }
    }
}
