package net.karolek.neoguilds.api.users;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface UserManager {

    User getUser(String name);

    User getUser(UUID uuid);

    User getUser(Player player);

    User createUser(Player player);

}
