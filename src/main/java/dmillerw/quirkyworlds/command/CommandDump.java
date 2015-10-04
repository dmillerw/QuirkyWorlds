package dmillerw.quirkyworlds.command;

import dmillerw.quirkyworlds.data.loader.DimensionLoader;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 * @author dmillerw
 */
public class CommandDump extends CommandBase {

    @Override
    public String getCommandName() {
        return "qdump";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        DimensionLoader.dump();
    }
}
