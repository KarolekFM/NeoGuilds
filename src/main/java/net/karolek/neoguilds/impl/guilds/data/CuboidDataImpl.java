package net.karolek.neoguilds.impl.guilds.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.Config;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.data.CuboidData;
import net.karolek.store.Queries;
import net.karolek.store.common.QueryCallback;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class CuboidDataImpl extends AbstractData<Guild> implements CuboidData {

    private World world;
    private int centerX;
    private int centerZ;
    private int size;
    private Location home;

    public CuboidDataImpl(Guild guild) {
        super(guild);
    }

    @Override
    public Location getCenter() {
        return new Location(world, centerX, Config.CUBOID_CRYSTAL$LEVEL, centerZ);
    }

    @Override
    public boolean addSize() {
        if (this.size >= Config.CUBOID_SIZE_MAX)
            return false;
        this.size += Config.CUBOID_SIZE_ADD;
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

    @Override
    public void loadData() {
        Queries.customQuery().query("SELECT * FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_cuboids` WHERE `guildUuid`='" + getT().getUUID() + "'").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                setWorld(Bukkit.getWorld(resultSet.getString("cuboidWorld")));
                setCenterX(resultSet.getInt("cuboidX"));
                setCenterZ(resultSet.getInt("cuboidZ"));
                setSize(resultSet.getInt("cuboidSize"));
                setHome(new Location(Bukkit.getWorld(resultSet.getString("homeWorld")), resultSet.getInt("homeX"), resultSet.getInt("homeY"), resultSet.getInt("homeZ")));
            }

            @Override
            public void error(Throwable throwable) {

            }
        }).execute(NeoAPI.getStore());
    }

    @Override
    public void saveData() {
        Queries.customQuery().query(
                "UPDATE `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_cuboids` SET `cuboidWorld`='" + getWorld().getName() + "',`cuboidX`=" + getCenterX() + ",`cuboidZ`=" + getCenterZ() + ",`cuboidSize`=" + getSize() + ",`homeWorld`='" + getHome().getWorld().getName() + "',`homeX`=" + getHome().getBlockX() + ",`homeY`=" + getHome().getBlockY() + ",`homeZ`=" + getHome().getBlockZ() + " WHERE `guildUuid`='" + getT().getUUID() + "'"
        ).execute(NeoAPI.getStore());
    }
}
