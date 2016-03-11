package net.karolek.neoguilds.listeners;

import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.extension.StatsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        User user = NeoAPI.getUserManager().getUser(p);
        if (user == null)
            user = NeoAPI.getUserManager().createUser(p);

        StatsData data = user.getData(StatsData.class);
        data.incrementPoints(165);
        data.decrementDeath();
        data.decrementDeath();
        data.setKills(data.getKills() + 10);
        data.saveData();

    }

}
