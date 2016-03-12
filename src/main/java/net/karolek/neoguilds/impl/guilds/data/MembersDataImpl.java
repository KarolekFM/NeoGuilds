package net.karolek.neoguilds.impl.guilds.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.NeoConfig;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.MemberType;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.utils.Debug;
import net.karolek.store.Queries;
import net.karolek.store.common.QueryCallback;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (!hasInvite(uuid))
            return false;

        if (isMember(uuid))
            return false;

        removeInvite(uuid);
        User user = NeoAPI.getUserManager().getUser(uuid);
        members.put(user, MemberType.MEMBER);
        toAdd.add(user);
        return true;
    }

    @Override
    public boolean removeMember(UUID uuid) {
        if (!isMember(uuid))
            return false;

        User user = NeoAPI.getUserManager().getUser(uuid);
        members.remove(user);
        toDelete.add(user);
        return true;
    }

    @Override
    public boolean addLeader(UUID uuid) {
        if (!isMember(uuid))
            return false;

        if (isOwner(uuid))
            return false;

        User user = NeoAPI.getUserManager().getUser(uuid);
        members.put(user, MemberType.LEADER);
        toUpdate.add(user);
        return true;
    }

    @Override
    public boolean removeLeader(UUID uuid) {
        if (!isMember(uuid))
            return false;

        User user = NeoAPI.getUserManager().getUser(uuid);
        members.put(user, MemberType.MEMBER);
        toUpdate.add(user);
        return true;
    }

    @Override
    public boolean addOwner(UUID uuid) {
        if (!isMember(uuid))
            return false;

        if (isLeader(uuid))
            return false;

        User user = NeoAPI.getUserManager().getUser(uuid);
        members.put(user, MemberType.OWNER);
        toUpdate.add(user);
        return true;
    }

    @Override
    public boolean removeOwner(UUID uuid) {
        if (!isMember(uuid))
            return false;

        User user = NeoAPI.getUserManager().getUser(uuid);
        members.put(user, MemberType.MEMBER);
        toUpdate.add(user);
        return true;
    }

    private MemberType getMemberType(User user) {
        return members.get(user);
    }

    @Override
    public void loadData() {
        Queries.customQuery().query("SELECT * FROM `" + NeoConfig.MYSQL_PREFIX + "guilds_members` WHERE `guildUuid`='" + getT().getUUID() + "'").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                while (resultSet.next()) {
                    User user = NeoAPI.getUserManager().getUser(resultSet.getString("userUuid"));
                    MemberType memberType = MemberType.valueOf(resultSet.getString("memberType"));
                    members.put(user, memberType);
                    Debug.debug("Loaded guild (" + getT().getTag() + ") member: " + user.getName() + ", type: " + memberType.name());
                }
            }

            @Override
            public void error(Throwable throwable) {

            }
        }).execute(NeoAPI.getStore());
    }

    @Override
    public void saveData() {
        for (User user : toAdd) {
            Queries.customQuery().query(
                    "INSERT INTO `" + NeoConfig.MYSQL_PREFIX + "guilds_members`(`id`, `guildUuid`, `userUuid`, `memberType`) VALUES (NULL,'" + getT().getUUID() + "','" + user.getUUID() + "','" + getMemberType(user) + "')"
            ).execute(NeoAPI.getStore());
        }
        for (User user : toUpdate) {
            Queries.customQuery().query(
                    "UPDATE `" + NeoConfig.MYSQL_PREFIX + "guilds_members` SET `guildUuid`='" + getT().getUUID() + "',`memberType`='" + getMemberType(user) + "' WHERE `userUuid`='" + user.getUUID() + "'"
            ).execute(NeoAPI.getStore());
        }
        for (User user : toDelete) {
            Queries.customQuery().query(
                    "DELETE FROM `" + NeoConfig.MYSQL_PREFIX + "guilds_members` WHERE `userUuid`='" + user.getUUID() + "'"
            ).execute(NeoAPI.getStore());
        }
    }
}
