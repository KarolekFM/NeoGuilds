package net.karolek.neoguilds.api.guilds.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.MemberType;
import net.karolek.neoguilds.api.users.User;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface MembersData extends Data<Guild> {

    Map<User, MemberType> getAllMembers();

    Map<Player, MemberType> getOnlineMembers();

    Set<User> getMembers();

    Set<User> getLeaders();

    Set<User> getOwners();

    Set<UUID> getInvites();

    boolean isMember(UUID uuid);

    boolean isLeader(UUID uuid);

    boolean isOwner(UUID uuid);

    boolean hasInvite(UUID uuid);

    boolean addInvite(UUID uuid);

    boolean removeInvite(UUID uuid);

    boolean addMember(UUID uuid);

    boolean removeMember(UUID uuid);

    boolean addLeader(UUID uuid);

    boolean removeLeader(UUID uuid);

    boolean addOwner(UUID uuid);

    boolean removeOwner(UUID uuid);

}
