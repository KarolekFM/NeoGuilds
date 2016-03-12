package net.karolek.neoguilds.api.users.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.users.User;
import org.bukkit.entity.Player;

public interface TabData extends Data<User> {

    Player getPlayer();

    void setPlayer(Player player);

    void sendTab();

    void setSlot(int row, int column, String text);

    void updateSlot(int row, int column, String text);

    void setHeaderAndFooter(String header, String footer);

}
