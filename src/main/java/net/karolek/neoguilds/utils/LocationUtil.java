package net.karolek.neoguilds.utils;

import org.bukkit.Location;

public final class LocationUtil {

    private LocationUtil() {
    }

    public static boolean equals(Location one, Location two) {
        return (one.getBlockX() == two.getBlockX() && one.getBlockY() == two.getBlockY() && one.getBlockZ() == two.getBlockZ());
    }

    public static boolean equalsFlat(Location one, Location two) {
        return (one.getBlockX() == two.getBlockX() && one.getBlockZ() == two.getBlockZ());

    }

}
