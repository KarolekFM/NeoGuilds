package net.karolek.neoguilds;

import lombok.Getter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.DataFactory;
import net.karolek.neoguilds.api.guilds.GuildManager;
import net.karolek.neoguilds.api.guilds.data.CuboidData;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.packets.PacketManager;
import net.karolek.neoguilds.api.users.UserManager;
import net.karolek.neoguilds.api.users.data.StatsData;
import net.karolek.neoguilds.api.users.data.TabData;
import net.karolek.neoguilds.configuration.fields.TabSlot;
import net.karolek.neoguilds.impl.guilds.GuildManagerImpl;
import net.karolek.neoguilds.impl.guilds.data.CuboidDataImpl;
import net.karolek.neoguilds.impl.guilds.data.MembersDataImpl;
import net.karolek.neoguilds.impl.packets.PacketManagerImpl;
import net.karolek.neoguilds.impl.users.UserManagerImpl;
import net.karolek.neoguilds.impl.users.data.StatsDataImpl;
import net.karolek.neoguilds.impl.users.data.TabDataImpl;
import net.karolek.neoguilds.listeners.PlayerJoinListener;
import net.karolek.store.Store;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class NeoGuilds extends JavaPlugin {

    private DataFactory dataFactory;
    private NeoConfig neoConfig;
    private NeoLang neoLang;
    private NeoTab neoTab;
    private Store store;
    private UserManager userManager;
    private PacketManager packetManager;
    private GuildManager guildManager;

    @Override
    public void onLoad() {
        ConfigurationSerialization.registerClass(TabSlot.class, "TabSlot");
        NeoAPI.setNeoGuilds(this);
        dataFactory = new DataFactory();
        NeoAPI.getDataFactory().register(StatsData.class, StatsDataImpl.class);
        NeoAPI.getDataFactory().register(TabData.class, TabDataImpl.class);
        NeoAPI.getDataFactory().register(MembersData.class, MembersDataImpl.class);
        NeoAPI.getDataFactory().register(CuboidData.class, CuboidDataImpl.class);
    }

    @Override
    public void onEnable() {
        neoConfig = new NeoConfig(this);
        neoLang = new NeoLang(this);
        neoTab = new NeoTab(this);
        store = Store.createMysql(new StoreTaskProvider(this), NeoConfig.MYSQL_HOST, NeoConfig.MYSQL_BASE, NeoConfig.MYSQL_USER, NeoConfig.MYSQL_PASS);
        store.setDebug(NeoConfig.DEBUG);
        userManager = new UserManagerImpl(this);
        packetManager = new PacketManagerImpl();
        guildManager = new GuildManagerImpl(this);


        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(), this);



    }

    @Override
    public void onDisable() {

        userManager = null;
        store.disconnect();
        neoLang = null;
        neoConfig = null;
    }
}
