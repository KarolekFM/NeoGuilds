package net.karolek.neoguilds.packets;

import net.karolek.neoguilds.api.Profile;
import org.bukkit.entity.Player;

public interface PacketManager {

    void sendPackets(Player player, Object... objects);

    void sendTablistHeaderPacket(Player player, String header, String footer);

    void sendPlayerListPacket(Player player, Profile profile, String displayName, PlayerInfoAction action);

}
