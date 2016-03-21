package net.karolek.neoguilds.commands;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.commands.exceptions.CommandException;
import net.karolek.neoguilds.commands.exceptions.PermissionException;
import net.karolek.neoguilds.utils.Reflection;
import net.karolek.neoguilds.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class AbstractCommand extends Command {

    private static final Reflection.FieldAccessor<SimpleCommandMap> f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
    private static CommandMap cmdMap = f.get(Bukkit.getServer().getPluginManager());

    protected final String name;
    protected final String usage;
    protected final String description;
    protected final String permission;
    protected final boolean onlyPlayer;


    //konsola + gracz
    public AbstractCommand(String name, String description, String usage, String permission, boolean onlyPlayer, String... aliases) {
        super(name, description, usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
    }

    //tylko gracz
    public AbstractCommand(String name, String description, String usage, String permission, String... aliases) {
        this(name, description, usage, permission, true, aliases);
    }

    public AbstractCommand(String name, String description, String usage, String permission, List<String> aliases) {
        super(name, description, usage, aliases);
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
        this.onlyPlayer = true;
    }

    public boolean runCommand(CommandSender sender, String[] args) throws CommandException {
        throw new CommandException("Default console void");
    }

    public boolean runCommand(Player sender, String[] args) throws CommandException {
        throw new CommandException("Default player void");
    }

    public void register() {
        if (cmdMap == null)
            cmdMap = f.get(Bukkit.getServer().getPluginManager());
        cmdMap.register(this.name, this);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        try {
            if (this.permission != null && this.permission.length() > 0) {
                if (!sender.hasPermission(this.permission))
                    throw new PermissionException(this.permission);
            }
            if (onlyPlayer) {
                if (!(sender instanceof Player))
                    throw new CommandException("&cMusisz byc graczem, aby moc wykonac ta komende!");
                return runCommand((Player) sender, strings);
            }
            return runCommand(sender, strings);
        } catch (CommandException e) {
            return Util.sendMsg(sender, e.getMessage());
        }
    }
}
