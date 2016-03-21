package net.karolek.neoguilds;

import lombok.Getter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.DataFactory;
import net.karolek.neoguilds.api.guilds.GuildManager;
import net.karolek.neoguilds.api.guilds.data.CuboidData;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.guilds.data.OtherData;
import net.karolek.neoguilds.api.packets.PacketManager;
import net.karolek.neoguilds.api.users.UserManager;
import net.karolek.neoguilds.api.users.data.StatsData;
import net.karolek.neoguilds.api.users.data.TabData;
import net.karolek.neoguilds.commands.guilds.GuildCommand;
import net.karolek.neoguilds.impl.guilds.GuildManagerImpl;
import net.karolek.neoguilds.impl.guilds.data.CuboidDataImpl;
import net.karolek.neoguilds.impl.guilds.data.MembersDataImpl;
import net.karolek.neoguilds.impl.guilds.data.OtherDataImpl;
import net.karolek.neoguilds.impl.packets.PacketManagerImpl;
import net.karolek.neoguilds.impl.users.UserManagerImpl;
import net.karolek.neoguilds.impl.users.data.StatsDataImpl;
import net.karolek.neoguilds.impl.users.data.TabDataImpl;
import net.karolek.neoguilds.listeners.PlayerJoinListener;
import net.karolek.store.Store;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class NeoGuilds extends JavaPlugin {

    private DataFactory dataFactory;
    private Config neoConfig;
    private Messages messages;
    private Commands commands;
    private Store store;
    private UserManager userManager;
    private PacketManager packetManager;
    private GuildManager guildManager;

    @Override
    public void onLoad() {
        NeoAPI.setNeoGuilds(this);
        dataFactory = new DataFactory();
        NeoAPI.getDataFactory().register(StatsData.class, StatsDataImpl.class);
        NeoAPI.getDataFactory().register(TabData.class, TabDataImpl.class);
        NeoAPI.getDataFactory().register(MembersData.class, MembersDataImpl.class);
        NeoAPI.getDataFactory().register(CuboidData.class, CuboidDataImpl.class);
        NeoAPI.getDataFactory().register(OtherData.class, OtherDataImpl.class);
    }

    @Override
    public void onEnable() {
        neoConfig = new Config(this);
        messages = new Messages(this, Config.LANG);
        commands = new Commands(this, Config.LANG);
        store = Store.createMysql(new StoreTaskProvider(this), Config.STORE_MYSQL_HOST, Config.STORE_MYSQL_BASENAME, Config.STORE_MYSQL_USERNAME, Config.STORE_MYSQL_PASSWORD);
        store.setDebug(Config.DEBUG);
        userManager = new UserManagerImpl(this);
        packetManager = new PacketManagerImpl();
        guildManager = new GuildManagerImpl(this);


        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), this);

        new GuildCommand().register();

    }

    @Override
    public void onDisable() {

    }
}
