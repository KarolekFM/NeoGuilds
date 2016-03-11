package net.karolek.neoguilds.impl.users.data;

import net.karolek.neoguilds.api.Profile;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.extension.TabData;
import net.karolek.neoguilds.utils.Util;

import java.util.UUID;

public class TabDataImpl extends UserDataImpl implements TabData {

    private static final int ROWS = 20;
    private static final int COLUMNS = 4;
    private static final Profile[][] PROFILES = new Profile[ROWS][COLUMNS];

    static {
        int base = 97;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 20; j++) {
                char first = (char) (base + i);
                char second = (char) (base + j);
                String name = "!!UPDATEMC" + first + "" + second;
                PROFILES[i][j] = new Profile(UUID.randomUUID(), name);
            }
        }
    }

    private final String[][] slots = new String[ROWS][COLUMNS];
    private boolean locked = false;

    public TabDataImpl(User user) {
        super(user);
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 20; row++) {
                this.slots[row][col] = "";
            }
        }
    }

    @Override
    public void sendTab() {
        locked = true;
        for (int col = 3; col >= 0; col--) {
            for (int row = 19; row >= 0; row--) {
                Profile profile = PROFILES[col][row];
                String slot = this.slots[col][row];
                //PacketUtil.sendPacket(this.player, new Object[] { TabUtil.createPlayerPacket(profile, slot.getText()) });
            }
        }
    }

    @Override
    public void setSlot(int row, int column, String text) {
        if (locked)
            throw new IllegalArgumentException("Can not set slot after tab send!");
        this.slots[column][row] = Util.fixColor(text);
    }

    @Override
    public void updateSlot(int row, int column, String text) {
        this.slots[column][row] = Util.fixColor(text);

        // PacketUtil.sendPacket(this.player, new Object[] { TabUtil.updatePlayerPacket(PROFILES[column][row], slot.getText()) });
    }

    @Override
    public void setHeaderAndFooter(String header, String footer) {
        //PacketUtil.sendPacket(this.player, new Object[] { TabUtil.createHeaderPacket(header, footer) });
    }

    @Override
    public void loadData() {
    }

    @Override
    public void saveData() {
    }

}
