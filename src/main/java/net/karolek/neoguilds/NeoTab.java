package net.karolek.neoguilds;

import net.karolek.neoguilds.configuration.Configuration;
import net.karolek.neoguilds.configuration.fields.TabSlot;
import net.karolek.neoguilds.configuration.fields.TabSlotType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class NeoTab extends Configuration {

    public static List<TabSlot> SLOTS = Arrays.asList(new TabSlot(0, 0, "Witaj!", TabSlotType.NORMAL), new TabSlot(1, 1, "1. {GTOP-1}", TabSlotType.TOP_GUILD), new TabSlot(1, 3, "1. {PTOP-1}", TabSlotType.TOP_PLAYER));

    public NeoTab(JavaPlugin plugin) {
        super(plugin, "tab.yml", "tab.");
    }
}
