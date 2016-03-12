package net.karolek.neoguilds.api.guilds;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface Cuboid {

    World getWorld();

    int getCenterX();

    int getCenterZ();

    Location getCenter();

    Location getHome();

    void setHome(Location location);

    int getSize();

    boolean addSize();

    boolean isInCuboid(Location loc);

    boolean isInCuboid(Player player);

    boolean isInCuboid(Block block);

}
