package net.karolek.neoguilds.api.users;

import net.karolek.neoguilds.api.users.data.UserData;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {

    UUID getUUID();

    String getName();

    Player getPlayer();

    <T extends UserData> void setData(Class<T> clazz, T t);

    <T extends UserData> T getData(Class<T> clazz);

}
