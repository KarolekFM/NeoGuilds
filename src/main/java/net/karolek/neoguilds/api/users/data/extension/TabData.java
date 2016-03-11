package net.karolek.neoguilds.api.users.data.extension;

import net.karolek.neoguilds.api.users.data.UserData;
import org.bukkit.entity.Player;

public interface TabData extends UserData {

    Player getPlayer();

    void setPlayer(Player player);

    void sendTab();

    void setSlot(int row, int column, String text);

    void updateSlot(int row, int column, String text);

    void setHeaderAndFooter(String header, String footer);

}
