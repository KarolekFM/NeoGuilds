package net.karolek.neoguilds;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.users.UserManager;
import net.karolek.neoguilds.api.users.data.DataFactory;
import net.karolek.neoguilds.impl.users.UserManagerImpl;
import net.karolek.neoguilds.listeners.PlayerJoinListener;
import net.karolek.store.Store;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class NeoGuilds extends JavaPlugin {

    private DataFactory dataFactory;
    private NeoConfig neoConfig;
    private NeoLang neoLang;
    private Store store;
    private UserManager userManager;

    @Override
    public void onLoad() {
        dataFactory = new DataFactory();
        NeoAPI.setNeoGuilds(this);
    }

    @Override
    public void onEnable() {
        neoConfig = new NeoConfig(this);
        neoLang = new NeoLang(this);
        store = Store.createMysql(new StoreTaskProvider(this), NeoConfig.MYSQL_HOST, NeoConfig.MYSQL_BASE, NeoConfig.MYSQL_USER, NeoConfig.MYSQL_PASS);
        store.setDebug(NeoConfig.DEBUG);
        userManager = new UserManagerImpl(this);


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
