package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import net.karolek.neoguilds.configuration.fields.Message;
import org.bukkit.plugin.java.JavaPlugin;

public class NeoLang extends Configuration {

    public static Message COMMANDS_NO$ENOGUH$ARGS = Message._("&7Prawidlowe uzycie: &a{0}");
    public static Message COMMANDS_NO$PERMISSIONS = Message._("&cNie masz praw do tej komendy! &7({0})");

    public NeoLang(JavaPlugin plugin) {
        super(plugin, "lang.yml", "lang.");
    }
}
