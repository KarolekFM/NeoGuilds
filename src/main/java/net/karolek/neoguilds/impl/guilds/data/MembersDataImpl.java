package net.karolek.neoguilds.impl.guilds.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.MemberType;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.users.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class MembersDataImpl extends AbstractData<Guild> implements MembersData {

    private final Map<User, MemberType> members = new HashMap<>();
    private final Set<UUID> invites = new HashSet<>();

    private final Set<User> toAdd = new HashSet<>();
    private final Set<User> toUpdate = new HashSet<>();
    private final Set<User> toDelete = new HashSet<>();

    public MembersDataImpl(Guild guild) {
        super(guild);
    }

    @Override
    public Map<User, MemberType> getAllMembers() {
        return members;
    }

    @Override
    public Map<Player, MemberType> getOnlineMembers() {
        Map<Player, MemberType> onlineMembers = new HashMap<>();
        for (Map.Entry<User, MemberType> e : members.entrySet()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(e.getKey().getUUID());
            if (op.isOnline()) onlineMembers.put(op.getPlayer(), e.getValue());
        }
        return onlineMembers;
    }

    @Override
    public Set<User> getMembers() {
        Set<User> users = new HashSet<>();
        for (Map.Entry<User, MemberType> e : members.entrySet())
            if (e.getValue() == MemberType.MEMBER) users.add(e.getKey());
        return users;
    }

    @Override
    public Set<User> getLeaders() {
        Set<User> leaders = new HashSet<>();
        for (Map.Entry<User, MemberType> e : members.entrySet())
            if (e.getValue() == MemberType.LEADER) leaders.add(e.getKey());
        return leaders;
    }

    @Override
    public Set<User> getOwners() {
        Set<User> owners = new HashSet<>();
        for (Map.Entry<User, MemberType> e : members.entrySet())
            if (e.getValue() == MemberType.LEADER) owners.add(e.getKey());
        return owners;
    }


    @Override
    public boolean isMember(UUID uuid) {
        for (User user : members.keySet()) if (user.getUUID().equals(uuid)) return true;
        return false;
    }

    @Override
    public boolean isLeader(UUID uuid) {
        for (User user : getLeaders()) if (user.getUUID().equals(uuid)) return true;
        return false;
    }

    @Override
    public boolean isOwner(UUID uuid) {
        for (User user : getOwners()) if (user.getUUID().equals(uuid)) return true;
        return false;
    }

    @Override
    public boolean hasInvite(UUID uuid) {
        return invites.contains(uuid);
    }

    @Override
    public boolean addInvite(UUID uuid) {
        if (hasInvite(uuid))
            return false;
        return invites.add(uuid);
    }

    @Override
    public boolean removeInvite(UUID uuid) {
        return invites.remove(uuid);
    }

    @Override
    public boolean addMember(UUID uuid) {
        return false;
    }

    @Override
    public boolean removeMember(UUID uuid) {
        return false;
    }

    @Override
    public boolean addLeader(UUID uuid) {
        return false;
    }

    @Override
    public boolean removeLeader(UUID uuid) {
        return false;
    }

    @Override
    public boolean addOwner(UUID uuid) {
        return false;
    }

    @Override
    public boolean removeOwner(UUID uuid) {
        return false;
    }

    @Override
    public void loadData() {

    }

    @Override
    public void saveData() {

    }
}
