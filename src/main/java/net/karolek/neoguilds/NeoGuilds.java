package net.karolek.neoguilds;

import lombok.Getter;
import lombok.Setter;
import net.karolek.store.Store;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class NeoGuilds extends JavaPlugin {

    private NeoConfig neoConfig;
    private NeoLang neoLang;
    private Store store;

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        neoConfig = new NeoConfig(this);
        neoLang = new NeoLang(this);
        store = Store.createMysql(new StoreTaskProvider(this), NeoConfig.MYSQL_HOST, NeoConfig.MYSQL_BASE, NeoConfig.MYSQL_USER, NeoConfig.MYSQL_PASS);


    }

    @Override
    public void onDisable() {

        store.disconnect();
        neoLang = null;
        neoConfig = null;
    }
}
