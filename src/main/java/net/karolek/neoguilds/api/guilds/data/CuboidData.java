package net.karolek.neoguilds.api.guilds.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.guilds.Guild;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface CuboidData extends Data<Guild> {

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
