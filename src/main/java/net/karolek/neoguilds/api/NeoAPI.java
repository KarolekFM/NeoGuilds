package net.karolek.neoguilds.api;

import net.karolek.neoguilds.Commands;
import net.karolek.neoguilds.Config;
import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.NeoGuilds;
import net.karolek.neoguilds.api.data.DataFactory;
import net.karolek.neoguilds.api.guilds.GuildManager;
import net.karolek.neoguilds.api.packets.PacketManager;
import net.karolek.neoguilds.api.users.UserManager;
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

    public static Config getConfig() {
        return getNeoGuilds().getNeoConfig();
    }

    public static Messages getMessages() {
        return getNeoGuilds().getMessages();
    }

    public static Commands getCommands() {
        return getNeoGuilds().getCommands();
    }

    public static DataFactory getDataFactory() {
        return getNeoGuilds().getDataFactory();
    }

    public static UserManager getUserManager() {
        return getNeoGuilds().getUserManager();
    }

    public static PacketManager getPacketManager() {
        return getNeoGuilds().getPacketManager();
    }

    public static GuildManager getGuildManager() {
        return getNeoGuilds().getGuildManager();
    }

}
