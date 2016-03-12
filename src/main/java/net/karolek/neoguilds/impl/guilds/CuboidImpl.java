package net.karolek.neoguilds.impl.guilds;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.NeoConfig;
import net.karolek.neoguilds.api.guilds.Cuboid;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@Setter
public class CuboidImpl implements Cuboid {

    private final World world;
    private final int centerX;
    private final int centerZ;
    private int size;
    private Location home;

    public CuboidImpl(Location location) {
        this.world = location.getWorld();
        this.centerX = location.getBlockX();
        this.centerZ = location.getBlockZ();
        this.size = NeoConfig.CUBOID_SIZE_START;
        this.home = location;
    }

    public CuboidImpl(Location location, int size, Location home) {
        this.world = location.getWorld();
        this.centerX = location.getBlockX();
        this.centerZ = location.getBlockZ();
        this.size = size;
        this.home = home;
    }

    @Override
    public Location getCenter() {
        return new Location(world, centerX, NeoConfig.CUBOID_CRYSTAL$LEVEL, centerZ);
    }

    @Override
    public boolean addSize() {
        if (this.size >= NeoConfig.CUBOID_SIZE_MAX)
            return false;
        this.size += NeoConfig.CUBOID_SIZE_ADD;
        return true;
    }

    @Override
    public boolean isInCuboid(Location loc) {
        if (!loc.getWorld().equals(this.world))
            return false;
        int distancex = Math.abs(loc.getBlockX() - this.centerX);
        int distancez = Math.abs(loc.getBlockZ() - this.centerZ);
        return (distancex <= this.size) && (distancez <= this.size);
    }

    @Override
    public boolean isInCuboid(Player player) {
        return isInCuboid(player.getLocation());
    }

    @Override
    public boolean isInCuboid(Block block) {
        return isInCuboid(block.getLocation());
    }
}
