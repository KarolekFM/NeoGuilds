package net.karolek.neoguilds.impl.guilds;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.users.User;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class GuildImpl implements Guild {

    private final UUID uuid;
    private final User creator;
    private final String tag;
    private final String name;
    private final Map<Class<? extends Data<Guild>>, AbstractData<Guild>> data = new HashMap<>();


    public GuildImpl(String tag, String name, Player player) {
        this.uuid = UUID.randomUUID();
        this.creator = NeoAPI.getUserManager().getUser(player);
        this.tag = tag;
        this.name = name;
    }

    public GuildImpl(ResultSet rs) throws SQLException {
        this.uuid = UUID.fromString(rs.getString("uuid"));
        this.creator = NeoAPI.getUserManager().getUser(rs.getString("creator"));
        this.tag = rs.getString("tag");
        this.name = rs.getString("name");
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public void setData(Class<? extends Data<Guild>> clazz, AbstractData<Guild> data) {
        this.data.put(clazz, data);
    }

    @Override
    public <U extends Data<Guild>> U getData(Class<U> clazz) {
        if (data.get(clazz) == null) {
            try {
                Class<? extends AbstractData> c = NeoAPI.getDataFactory().getFactory(clazz);
                AbstractData<Guild> data = c.getConstructor(Guild.class).newInstance(this);
                setData(clazz, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (U) data.get(clazz);
    }


}
