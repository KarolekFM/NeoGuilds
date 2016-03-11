package net.karolek.neoguilds.api;

import net.karolek.neoguilds.NeoConfig;
import net.karolek.neoguilds.NeoGuilds;
import net.karolek.neoguilds.NeoLang;
import net.karolek.store.Store;

public final class NeoAPI {

    private static NeoGuilds neoGuilds;

    private NeoAPI() {
    }

    public static NeoGuilds getNeoGuilds() {
        if (NeoAPI.neoGuilds == null)
            throw new IllegalArgumentException("You must set plugin! Please use setNeoGuilds method!");
        return NeoAPI.neoGuilds;
    }

    public static void setNeoGuilds(NeoGuilds neoGuilds) {
        if (NeoAPI.neoGuilds != null) throw new IllegalArgumentException("Can not set plugin second time!");
        NeoAPI.neoGuilds = neoGuilds;
    }

    public static Store getStore() {
        return getNeoGuilds().getStore();
    }

    public static NeoConfig getNeoConfig() {
        return getNeoGuilds().getNeoConfig();
    }

    public static NeoLang getNeoLang() {
        return getNeoGuilds().getNeoLang();
    }

}
