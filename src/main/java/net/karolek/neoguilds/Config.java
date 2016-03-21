package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Config extends Configuration {

    public static boolean DEBUG = true;

    public static String LANG = "PL";

    public static String STORE_TYPE = "mysql";
    public static String STORE_MYSQL_HOST = "localhost:3306";
    public static String STORE_MYSQL_USERNAME = "root";
    public static String STORE_MYSQL_PASSWORD = "password";
    public static String STORE_MYSQL_BASENAME = "basename";
    public static String STORE_MYSQL_TABLE$PREFIX = "neoguilds_";

    public static int RANKING_START$POINTS = 1000;

    public static int NAME_LENGHT$MIN = 2;
    public static int NAME_LENGHT$MAX = 22;
    public static String NAME_REGEX = "[a-zA-Z]+";

    public static int TAG_LENGHT$MIN = 2;
    public static int TAG_LENGHT$MAX = 5;
    public static String TAG_REGEX = "[a-zA-Z]+";

    public static String CUBOID_WORLD = "world";
    public static int CUBOID_CRYSTAL$LEVEL = 50;
    public static String CUBOID_CRYSTAL$BLOCK = "dragon egg";
    public static int CUBOID_SIZE_START = 24;
    public static int CUBOID_SIZE_MAX = 74;
    public static int CUBOID_SIZE_ADD = 1;
    public static int CUBOID_SIZE_BETWEEN = 50;


    public static List<String> ITEMS_CREATE_NORMAL = Arrays.asList("64 diamond", "64 emerald");


    public Config(JavaPlugin plugin) {
        super(plugin, "config.yml", "config.");
    }


}
