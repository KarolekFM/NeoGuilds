package net.karolek.neoguilds.api.users;

import net.karolek.neoguilds.api.data.DataExtension;

import java.util.UUID;

public interface User extends DataExtension<User> {

    UUID getUUID();

    String getName();

}
