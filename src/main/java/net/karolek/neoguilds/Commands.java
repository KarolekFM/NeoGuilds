package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Commands extends Configuration {

    public static String GUILD_USER_MAIN_NAME = "gildia";
    public static String GUILD_USER_MAIN_DESCRIPTION = "glowna komenda NeoGUILDS";
    public static String GUILD_USER_MAIN_USAGE = "/gildia <subkomenda>";
    public static String GUILD_USER_MAIN_PERMISSION = "neoguilds.commands.guild";
    public static List<String> GUILD_USER_MAIN_ALIASES = Arrays.asList("g", "gildie", "guild");
    public static String GUILD_USER_CREATE_NAME = "zaloz";
    public static String GUILD_USER_CREATE_DESCRIPTION = "tworzy nowa gildie";
    public static String GUILD_USER_CREATE_USAGE = "/g zaloz <tag> <nazwa>";
    public static String GUILD_USER_CREATE_PERMISSION = "neoguilds.commands.guild.create";
    public static List<String> GUILD_USER_CREATE_ALIASES = Arrays.asList("create", "utworz", "nowa");
    public static String GUILD_USER_DELETE_NAME = "usun";
    public static String GUILD_USER_DELETE_DESCRIPTION = "usuwa gildie";
    public static String GUILD_USER_DELETE_USAGE = "/g usun";
    public static String GUILD_USER_DELETE_PERMISSION = "neoguilds.commands.guild.delete";
    public static List<String> GUILD_USER_DELETE_ALIASES = Arrays.asList("delete", "skasuj");

    public Commands(JavaPlugin plugin, String lang) {
        super(plugin, "commands-" + lang + ".yml", "commands.");
    }
}
