package net.karolek.neoguilds.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.List;

public final class Util {

    private Util() {
    }

    public static String fixColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] fixColor(String[] s) {
        for (int i = 0; i < s.length; i++) s[i] = fixColor(s[i]);
        return s;
    }

    public static List<String> fixColor(List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            String string = s.get(i);
            s.set(i, fixColor(string));
        }
        return s;
    }

    public static boolean sendMsg(CommandSender sender, String message) {
        sender.sendMessage(fixColor(message));
        return true;
    }

    public static boolean sendMsg(Collection<? extends CommandSender> col, String message) {
        for (CommandSender cs : col) sendMsg(cs, message);
        return true;
    }

    public static boolean sendMsg(CommandSender sender, Collection<String> messages) {
        for (String s : messages) sendMsg(sender, s);
        return true;
    }

    public static boolean sendMsg(Collection<? extends CommandSender> col, Collection<String> messages) {
        for (String s : messages) sendMsg(col, s);
        return true;
    }

    public static boolean sendMsg(CommandSender sender, String message, String permission) {
        if (!sender.hasPermission(permission)) return false;
        return sendMsg(sender, message);
    }

    public static boolean sendMsg(Collection<? extends CommandSender> col, String message, String permission) {
        for (CommandSender cs : col) {
            if (!cs.hasPermission(permission)) continue;
            sendMsg(cs, message);
        }
        return true;
    }

    public static boolean sendMsg(Collection<? extends CommandSender> col, Collection<String> messages, String permission) {
        for (CommandSender cs : col) {
            if (!cs.hasPermission(permission)) continue;
            sendMsg(cs, messages);
        }
        return true;
    }


}
