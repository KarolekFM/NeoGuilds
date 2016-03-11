package net.karolek.neoguilds.utils;

import net.karolek.neoguilds.NeoConfig;

public final class Debug {

    private Debug() {
    }

    public static boolean debug(String... strings) {
        if (NeoConfig.DEBUG)
            for (String s : strings) {
                System.out.println("[NeoGuilds] [DEBUG] > " + s);
            }
        return true;
    }


}
