package net.karolek.neoguilds.utils;

import net.karolek.neoguilds.Config;

public final class Debug {

    private Debug() {
    }

    public static boolean debug(String... strings) {
        if (Config.DEBUG)
            for (String s : strings) {
                System.out.println("[NeoGuilds] [DEBUG] > " + s);
            }
        return true;
    }


}
