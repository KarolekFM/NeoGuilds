package net.karolek.neoguilds.impl.guilds;

import net.karolek.neoguilds.Config;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.GuildManager;
import net.karolek.neoguilds.api.guilds.data.CuboidData;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.utils.Debug;
import net.karolek.store.Queries;
import net.karolek.store.common.QueryCallback;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class GuildManagerImpl implements GuildManager {

    private final JavaPlugin plugin;
    private final Set<Guild> guilds = new HashSet<>();

    public GuildManagerImpl(JavaPlugin plugin) {
        this.plugin = plugin;

        Queries.customQuery().query("SELECT * FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds`").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    Guild g = new GuildImpl(resultSet);
                    Debug.debug("Loaded guild: " + g.getTag() + ", " + g.getName() + ",  " + g.getUUID());
                    guilds.add(g);

                }
            }

            @Override
            public void error(Throwable throwable) {
                plugin.getLogger().log(Level.SEVERE, "An error occured while loading guilds");
            }
        }).execute(NeoAPI.getStore());

    }

    @Override
    public Guild createGuild(String tag, String name, Player player) {
        Guild guild = new GuildImpl(tag, name, player);
        guilds.add(guild);

        Location l = player.getLocation();

        Queries.customQuery().query(
                "INSERT INTO `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds`(`id`, `uuid`, `tag`, `name`, `creator`) VALUES (NULL,'" + guild.getUUID() + "','" + guild.getTag() + "','" + guild.getName() + "','" + player.getUniqueId() + "')"
        ).execute(NeoAPI.getStore());
        Queries.customQuery().query(
                "INSERT INTO `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_members`(`id`, `guildUuid`, `userUuid`, `memberType`) VALUES (NULL,'" + guild.getUUID() + "','" + player.getUniqueId() + "','OWNER')"
        ).execute(NeoAPI.getStore());
        Queries.customQuery().query(
                "INSERT INTO `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_cuboids`(`id`, `guildUuid`, `cuboidWorld`, `cuboidX`, `cuboidZ`, `cuboidSize`, `homeWorld`, `homeX`, `homeY`, `homeZ`) VALUES (NULL,'" + guild.getUUID() + "','" + l.getWorld().getName() + "'," + l.getBlockX() + "," + l.getBlockZ() + "," + Config.CUBOID_SIZE_START + ",'" + l.getWorld().getName() + "'," + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + ")"
        ).execute(NeoAPI.getStore());
        guild.getData(MembersData.class);
        guild.getData(CuboidData.class);

        player.getLocation().getBlock().setType(Material.matchMaterial(Config.CUBOID_CRYSTAL$BLOCK));

        return guild;
    }

    @Override
    public boolean deleteGuild(Guild guild) {
        guilds.remove(guild);
        Queries.customQuery().query("DELETE FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds` WHERE `uuid`='" + guild.getUUID() + "'").execute(NeoAPI.getStore());
        Queries.customQuery().query("DELETE FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_members` WHERE `guildUuid`='" + guild.getUUID() + "'").execute(NeoAPI.getStore());
        Queries.customQuery().query("DELETE FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "guilds_cuboids` WHERE `guildUuid`='" + guild.getUUID() + "'").execute(NeoAPI.getStore());
        return false;
    }

    @Override
    public Guild getGuild(String string) {
        for (Guild g : guilds)
            if (g.getTag().equalsIgnoreCase(string) || g.getName().equalsIgnoreCase(string)) return g;
        return null;
    }

    @Override
    public Guild getGuild(Player player) {
        for (Guild g : guilds)
            if (g.getData(MembersData.class).isMember(player.getUniqueId())) return g;
        return null;
    }

    @Override
    public Guild getGuild(User user) {
        for (Guild g : guilds)
            if (g.getData(MembersData.class).isMember(user.getUUID())) return g;
        return null;
    }

    @Override
    public Guild getGuild(Location location) {
        for (Guild g : guilds)
            if (g.getData(CuboidData.class).isInCuboid(location)) return g;
        return null;
    }

    @Override
    public boolean canCreateGuild(Location location) {
        if (!location.getWorld().getName().equals(Config.CUBOID_WORLD))
            return false;
        int mindistance = Config.CUBOID_SIZE_MAX * 2 + Config.CUBOID_SIZE_BETWEEN;
        for (Guild g : guilds) {
            CuboidData data = g.getData(CuboidData.class);
            if ((Math.abs(data.getCenterX() - location.getBlockX()) <= mindistance) && (Math.abs(data.getCenterZ() - location.getBlockZ()) <= mindistance))
                return false;
        }
        return true;
    }
}
