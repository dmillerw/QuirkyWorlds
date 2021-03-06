package dmillerw.quirkyworlds.command;

import dmillerw.quirkyworlds.util.EntityUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

/**
 * @author dmillerw
 */
public class CommandTPX extends CommandBase {

    @Override
    public String getCommandName() {
        return "qtp";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qtp [dimension]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 1)
            throw new CommandException(getCommandUsage(sender));

        if (!EntityUtils.teleportToDimension((EntityPlayer) sender, Integer.parseInt(args[0]))) {
            ((EntityPlayer) sender).addChatComponentMessage(new ChatComponentText("Invalid dimension: " + args[0]));
        }
    }
}
