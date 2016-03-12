package net.karolek.neoguilds.api.guilds;

import net.karolek.neoguilds.api.data.DataExtension;
import net.karolek.neoguilds.api.users.User;

import java.util.UUID;

public interface Guild extends DataExtension<Guild> {

    UUID getUUID();

    String getTag();

    String getName();

    User getCreator();

}
