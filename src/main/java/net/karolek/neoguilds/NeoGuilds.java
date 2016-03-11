package net.karolek.neoguilds;

import lombok.Getter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.packets.PacketManager;
import net.karolek.neoguilds.api.users.UserManager;
import net.karolek.neoguilds.api.users.data.DataFactory;
import net.karolek.neoguilds.api.users.data.extension.StatsData;
import net.karolek.neoguilds.api.users.data.extension.TabData;
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
    private NeoConfig neoConfig;
    private NeoLang neoLang;
    private Store store;
    private UserManager userManager;
    private PacketManager packetManager;

    @Override
    public void onLoad() {
        NeoAPI.setNeoGuilds(this);
        dataFactory = new DataFactory();
        NeoAPI.getDataFactory().register(StatsData.class, StatsDataImpl.class);
        NeoAPI.getDataFactory().register(TabData.class, TabDataImpl.class);
    }

    @Override
    public void onEnable() {
        neoConfig = new NeoConfig(this);
        neoLang = new NeoLang(this);
        store = Store.createMysql(new StoreTaskProvider(this), NeoConfig.MYSQL_HOST, NeoConfig.MYSQL_BASE, NeoConfig.MYSQL_USER, NeoConfig.MYSQL_PASS);
        store.setDebug(NeoConfig.DEBUG);
        userManager = new UserManagerImpl(this);
        packetManager = new PacketManagerImpl();


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
