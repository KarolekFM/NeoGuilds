package net.karolek.neoguilds.utils;

import org.bukkit.command.CommandSender;

import java.util.Collection;

public final class ChatUtil {

    private ChatUtil() {
    }

    public static boolean send(CommandSender sender, String message, Object... objects) {
        String replace = message;
        for (int i = 0; i < objects.length; i++)
            replace = replace.replace("{" + i + "}", objects[i].toString());
        return Util.sendMsg(sender, replace);

    }

    public static boolean send(Collection<? extends CommandSender> col, String message, Object... objects) {
        String replace = message;
        for (int i = 0; i < objects.length; i++)
            replace = replace.replace("{" + i + "}", objects[i].toString());
        return Util.sendMsg(col, replace);
    }

    public static String getMessage(String message, Object... replacements) {
        String replace = message;
        for (int i = 0; i < replacements.length; i++)
            replace = replace.replace("{" + i + "}", replacements[i].toString());
        return Util.fixColor(replace);
    }


}
