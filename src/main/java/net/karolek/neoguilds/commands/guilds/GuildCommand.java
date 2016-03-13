package net.karolek.neoguilds.commands.guilds;

import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.commands.AbstractCommand;
import net.karolek.neoguilds.commands.exceptions.CommandException;
import net.karolek.neoguilds.commands.exceptions.PermissionException;
import net.karolek.neoguilds.commands.guilds.user.CreateCommand;
import net.karolek.neoguilds.utils.Util;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class GuildCommand extends AbstractCommand {

    private static final LinkedHashSet<AbstractCommand> subCommands = new LinkedHashSet();

    public GuildCommand() {
        super("gildia", "glowna komenda gildii", "/g", "neoguilds.commands.guild", "g", "guild", "gildie");
        subCommands.add(new CreateCommand());
    }

    @Override
    public boolean runCommand(Player sender, String[] args) throws CommandException {
        if (args.length == 0) return Util.sendMsg(sender, Messages.COMMANDS_GUILDS$HELP);

        String name = args[0];

        AbstractCommand sc = getSub(name);

        if (sc == null) return Util.sendMsg(sender, Messages.COMMANDS_GUILDS$HELP);

        if (!sender.hasPermission(sc.getPermission()))
            throw new PermissionException(sc);

        return sc.runCommand(sender, Arrays.copyOfRange(args, 1, args.length));
    }


    private AbstractCommand getSub(String sub) {
        for (AbstractCommand sc : subCommands)
            if ((sc.getName().equalsIgnoreCase(sub)) || (sc.getAliases().contains(sub)))
                return sc;
        return null;
    }
}
