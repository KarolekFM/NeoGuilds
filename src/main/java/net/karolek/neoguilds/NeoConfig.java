package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import net.karolek.neoguilds.configuration.annotations.Comment;
import net.karolek.neoguilds.configuration.annotations.Ignore;
import org.bukkit.plugin.java.JavaPlugin;

public class NeoConfig extends Configuration {

    @Ignore
    public static boolean DEBUG = true;

    @Comment(value = "host do polaczenia sie z baza danych, np: localhost:3306")
    public static String MYSQL_HOST = "s1.speedhost.pl:3306";

    @Comment(value = "uzytkownik bazy danych")
    public static String MYSQL_USER = "sid1947_karolek";

    @Comment(value = "haslo uzytkownika bazy danych'")
    public static String MYSQL_PASS = "123321";

    @Comment(value = "nazwa bazy danych")
    public static String MYSQL_BASE = "sid1947_karolek";

    public NeoConfig(JavaPlugin plugin) {
        super(plugin, "config.yml", "config.");
    }
}
