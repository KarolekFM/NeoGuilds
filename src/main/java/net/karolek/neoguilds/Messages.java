package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Messages extends Configuration {

    public static String COMMANDS_NO$PERMISSIONS = "&cNie masz praw do wykonanie tej komendy! ({0})";
    public static String COMMANDS_NO$ENOUGH$ARGS = "&cPoprawne uzycie: {0}";

    public static List<String> COMMANDS_GUILDS$HELP = Arrays.asList("&8&m-----&r &f&lNeo&6&lGUILDS &8&m-----", " &8 > &c/g zaloz <tag> <nazwa> &7- tworzenie nowej gildii");

    public static String ERROR_ALREADY$HAVE$GUILD = "&4Blad: &cPosiadasz juz gildie!";
    public static String ERROR_TAG$INCORRECT$LENGTH = "&4Blad: &cTag musi miec od {0} do {1} znakow!";
    public static String ERROR_TAG$INCORRECT$CHARACTERS = "&4Blad: &cTag musi byc alfanumerczny ({0})";
    public static String ERROR_NAME$INCORRECT$LENGTH = "&4Blad: &cNazwa musi miec od {0} do {1} znakow!";
    public static String ERROR_NAME$INCORRECT$CHARACTERS = "&4Blad: &cNazwa musi byc alfanumerczna ({0})";
    public static String ERROR_GUILD$ALREADY$EXISTS = "&4Blad: &cTaka gildia juz istnieje!";
    public static String ERROR_GUILD$NEARBY = "&4Blad: &cW poblizu znajduje sie gildia!";

    public static String INFO_GUILD$CREATED = "&cUtworzyles gildie [{0}] {1}!";

    public static String BROADCAST_GUILD$CREATED = "&cGildia &7[{0}] {1} &czostala utworzona przez &7{2}&c!";


    public Messages(JavaPlugin plugin, String lang) {
        super(plugin, "messages-" + lang + ".yml", "messages.");
    }

}
