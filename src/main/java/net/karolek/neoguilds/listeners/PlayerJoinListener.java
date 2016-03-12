package net.karolek.neoguilds.listeners;

import net.karolek.neoguilds.NeoTab;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.TabData;
import net.karolek.neoguilds.configuration.fields.TabSlot;
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

        TabData tab = user.getData(TabData.class);
        tab.setPlayer(p);


        for (TabSlot slot : NeoTab.SLOTS) {
            tab.setSlot(slot.getRow(), slot.getColumn(), slot.getString());
        }
        tab.setHeaderAndFooter("&f&lNeo&6&lGUILDS &7v1.0.0\n&7&m-----------------------------------------------------------", "&7&m-----------------------------------------------------------\n&6Podoba Ci sie moj plugin? Prosze wesprzyj mnie! :)");

        tab.sendTab();

    }

}
