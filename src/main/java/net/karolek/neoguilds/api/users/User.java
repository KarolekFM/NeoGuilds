package net.karolek.neoguilds.api.users;

import net.karolek.neoguilds.api.users.data.UserData;

import java.util.UUID;

public interface User {

    UUID getUUID();

    String getName();

    void setData(Class<? extends UserData> clazz, UserData userData);

    <T extends UserData> T getData(Class<T> clazz);

}
