package net.karolek.neoguilds.impl.users;

import lombok.Getter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.UserData;
import net.karolek.neoguilds.impl.users.data.UserDataImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserImpl implements User {

    private final UUID uuid;
    private final String name;
    private final Map<Class<? extends UserData>, UserData> userData = new HashMap<>();

    public UserImpl(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UserImpl(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public UserImpl(ResultSet rs) throws SQLException {
        this.uuid = UUID.fromString(rs.getString("uuid"));
        this.name = rs.getString("name");
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public <T extends UserData> void setData(Class<T> clazz, T t) {
        this.userData.put(clazz, t);
    }

    @Override
    public <T extends UserData> T getData(Class<T> clazz) {
        if (userData.get(clazz) == null) {
            Class<? extends UserDataImpl> c = NeoAPI.getDataFactory().getFactory(clazz);
            try {
                UserData data = c.getConstructor(User.class).newInstance(this);
                setData(clazz, (T) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) userData.get(clazz);
    }
}
