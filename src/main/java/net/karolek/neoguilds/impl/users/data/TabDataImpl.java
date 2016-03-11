package net.karolek.neoguilds.impl.users.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.Profile;
import net.karolek.neoguilds.api.packets.PlayerInfoAction;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.extension.TabData;
import net.karolek.neoguilds.utils.Util;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class TabDataImpl extends UserDataImpl implements TabData {

    private static final int ROWS = 20;
    private static final int COLUMNS = 4;
    private static final Profile[][] PROFILES = new Profile[ROWS][COLUMNS];

    static {
        int base = 97;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                char first = (char) (base + col);
                char second = (char) (base + row);
                String name = "!!UPDATEMC" + first + "" + second;
                PROFILES[row][col] = new Profile(UUID.randomUUID(), name);
            }
        }
    }

    private final String[][] slots = new String[ROWS][COLUMNS];
    private boolean locked = false;
    private Player player;

    public TabDataImpl(User user) {
        super(user);
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                this.slots[row][col] = "";
            }
        }
    }

    @Override
    public void sendTab() {
        locked = true;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Profile profile = PROFILES[row][col];
                String slot = this.slots[row][col];
                NeoAPI.getPacketManager().sendPlayerListPacket(getPlayer(), profile, slot, PlayerInfoAction.ADD_PLAYER);
            }
        }
    }

    @Override
    public void setSlot(int row, int column, String text) {
        if (locked)
            throw new IllegalArgumentException("Can not set slot after tab send!");
        this.slots[row][column] = Util.fixColor(text);
    }

    @Override
    public void updateSlot(int row, int column, String text) {
        this.slots[row][column] = Util.fixColor(text);
        NeoAPI.getPacketManager().sendPlayerListPacket(getPlayer(), PROFILES[row][column], slots[row][column], PlayerInfoAction.UPDATE_DISPLAY_NAME);
    }

    @Override
    public void setHeaderAndFooter(String header, String footer) {
        NeoAPI.getPacketManager().sendTablistHeaderPacket(getPlayer(), header, footer);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void saveData() {
    }

}
