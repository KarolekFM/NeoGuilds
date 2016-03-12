package net.karolek.neoguilds.api.guilds;

import net.karolek.neoguilds.api.users.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface GuildManager {

    Guild createGuild(String tag, String name, Player player);

    boolean deleteGuild(Guild guild);

    Guild getGuild(String string);

    Guild getGuild(Player player);

    Guild getGuild(User user);

    Guild getGuild(Location location);

    boolean canCreateGuild(Location location);

}
