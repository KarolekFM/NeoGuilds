package net.karolek.neoguilds.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public final class TimeUtil {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final LinkedHashMap<Integer, String> values = new LinkedHashMap(6);

    static {
        values.put(31104000, "y");
        values.put(2592000, "msc");
        values.put(86400, "d");
        values.put(3600, "h");
        values.put(60, "min");
        values.put(1, "s");
    }

    private TimeUtil() {
    }

    public static String formatTime(long value) {
        return timeFormat.format(new Date(value));
    }

    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    public static String secondsToString(int seconds) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, String> e : values.entrySet()) {
            int iDiv = seconds / e.getKey();
            if (iDiv >= 1) {
                int x = (int) Math.floor(iDiv);
                sb.append(x + e.getValue()).append(" ");
                seconds -= x * e.getKey();
            }
        }
        return sb.toString();
    }

}
