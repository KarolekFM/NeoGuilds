package net.karolek.neoguilds;

import net.karolek.store.common.TaskProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class StoreTaskProvider implements TaskProvider {

    private final JavaPlugin plugin;
    private final ExecutorService executorService;

    public StoreTaskProvider(JavaPlugin plugin) {
        this.plugin = plugin;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public Logger getLogger() {
        return plugin.getLogger();
    }

    @Override
    public void runTask(Runnable runnable) {
        executorService.execute(runnable);
    }

    @Override
    public void runTaskTimer(Runnable runnable, long l) {
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, 1L, l);
    }
}
