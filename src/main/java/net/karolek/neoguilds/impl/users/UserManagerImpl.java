package net.karolek.neoguilds.impl.users;

import net.karolek.neoguilds.Config;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.UserManager;
import net.karolek.neoguilds.utils.Debug;
import net.karolek.store.Queries;
import net.karolek.store.common.QueryCallback;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class UserManagerImpl implements UserManager {

    private final Map<UUID, User> users = new HashMap<>();

    private final JavaPlugin plugin;

    public UserManagerImpl(JavaPlugin plugin) {
        this.plugin = plugin;
        Queries.customQuery().query("SELECT * FROM `" + Config.STORE_MYSQL_TABLE$PREFIX + "users`").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    User u = new UserImpl(resultSet);
                    Debug.debug("Loaded user: " + u.getName() + " (" + u.getUUID() + ")");
                    users.put(u.getUUID(), u);
                }
            }

            @Override
            public void error(Throwable throwable) {
                plugin.getLogger().log(Level.SEVERE, "An error occured while loading users");
            }
        }).execute(NeoAPI.getStore());
    }

    @Override
    public User getUser(String name) {
        for (User user : users.values())
            if (user.getName().equalsIgnoreCase(name)) return user;
        return null;
    }

    @Override
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public User getUser(Player player) {
        return users.get(player.getUniqueId());
    }

    @Override
    public User createUser(Player player) {
        User user = new UserImpl(player);
        Queries.customQuery().query(
                "INSERT INTO `" + Config.STORE_MYSQL_TABLE$PREFIX + "users`(`id`, `uuid`, `name`) VALUES (NULL,'" + user.getUUID() + "','" + user.getName() + "')"
        ).execute(NeoAPI.getStore());
        users.put(user.getUUID(), user);
        return user;
    }
}

