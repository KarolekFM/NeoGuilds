package net.karolek.neoguilds.configuration.fields;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@SerializableAs("TabSlot")
public class TabSlot implements ConfigurationSerializable {

    private final int row;
    private final int column;
    private final String string;
    private final TabSlotType tabSlotType;

    public TabSlot(int row, int column, String string, TabSlotType tabSlotType) {
        this.row = row;
        this.column = column;
        this.string = string;
        this.tabSlotType = tabSlotType;
    }

    public static TabSlot deserialize(Map<String, Object> map) {
        int row = -1;
        int column = -1;
        String string = null;
        TabSlotType tabSlotType = null;
        if (map.containsKey("row"))
            row = ((Number) map.get("row")).intValue();
        if (map.containsKey("column"))
            column = ((Number) map.get("column")).intValue();
        if (map.containsKey("string"))
            string = (String) map.get("string");
        if (map.containsKey("slot-type"))
            tabSlotType = TabSlotType.valueOf((String) map.get("slot-type"));
        return new TabSlot(row, column, string, tabSlotType);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("row", getRow());
        map.put("column", getColumn());
        map.put("string", getString());
        map.put("slot-type", getTabSlotType().name());
        return map;
    }
}
