package net.karolek.neoguilds.commands.guilds.user;

import net.karolek.neoguilds.Commands;
import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.data.MembersData;
import net.karolek.neoguilds.api.guilds.data.OtherData;
import net.karolek.neoguilds.commands.AbstractCommand;
import net.karolek.neoguilds.commands.exceptions.CommandException;
import net.karolek.neoguilds.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteCommand extends AbstractCommand {

    public DeleteCommand() {
        super(
                Commands.GUILD_USER_DELETE_NAME,
                Commands.GUILD_USER_DELETE_DESCRIPTION,
                Commands.GUILD_USER_DELETE_USAGE,
                Commands.GUILD_USER_DELETE_PERMISSION,
                Commands.GUILD_USER_DELETE_ALIASES
        );
    }

    @Override
    public boolean runCommand(Player sender, String[] args) throws CommandException {
        Guild g = NeoAPI.getGuildManager().getGuild(sender);

        if (g == null)
            return ChatUtil.send(sender, Messages.ERROR_DONT$HAVE$GUILD);

        if (!g.getData(MembersData.class).isOwner(sender.getUniqueId()))
            return ChatUtil.send(sender, Messages.ERROR_YOU$ARENT$OWNER);

        OtherData data = g.getData(OtherData.class);

        if (!data.isPreDeleted()) {

            data.setPreDeleted(true);

            ChatUtil.send(sender, Messages.INFO_CONFIRM$DELETE$GUILD);

            new BukkitRunnable() {
                @Override
                public void run() {
                    data.setPreDeleted(false);
                }
            }.runTaskLater(NeoAPI.getNeoGuilds(), 20 * 10);

        }

        NeoAPI.getGuildManager().deleteGuild(g);

        ChatUtil.send(sender, Messages.INFO_GUILD$DELETED, g.getTag(), g.getName(), sender.getName());
        return ChatUtil.send(sender, Messages.BROADCAST_GUILD$DELETED, g.getTag(), g.getName(), sender.getName());

    }
}
