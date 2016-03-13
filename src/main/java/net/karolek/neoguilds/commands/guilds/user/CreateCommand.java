package net.karolek.neoguilds.commands.guilds.user;

import net.karolek.neoguilds.Config;
import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.commands.AbstractCommand;
import net.karolek.neoguilds.commands.exceptions.CommandException;
import net.karolek.neoguilds.commands.exceptions.NoEnoughArgsException;
import net.karolek.neoguilds.utils.ChatUtil;
import org.bukkit.entity.Player;

public class CreateCommand extends AbstractCommand {

    public CreateCommand() {
        super("zaloz", "tworzy nowa gildie", "zaloz <tag> <nazwa>", "neoguilds.commands.guild.create", "create");
    }

    @Override
    public boolean runCommand(Player sender, String[] args) throws CommandException {
        if (args.length != 2)
            throw new NoEnoughArgsException(this);

        String tag = args[0];
        String name = args[1];

        if (NeoAPI.getGuildManager().getGuild(sender) != null)
            return ChatUtil.send(sender, Messages.ERROR_ALREADY$HAVE$GUILD);

        if (tag.length() < Config.TAG_LENGHT$MIN || tag.length() > Config.TAG_LENGHT$MAX)
            return ChatUtil.send(sender, Messages.ERROR_TAG$INCORRECT$LENGTH, Config.TAG_LENGHT$MIN, Config.TAG_LENGHT$MAX);

        if (name.length() < Config.NAME_LENGHT$MIN || name.length() > Config.NAME_LENGHT$MAX)
            return ChatUtil.send(sender, Messages.ERROR_NAME$INCORRECT$LENGTH, Config.NAME_LENGHT$MIN, Config.NAME_LENGHT$MAX);

        if (!tag.matches(Config.TAG_REGEX))
            return ChatUtil.send(sender, Messages.ERROR_TAG$INCORRECT$CHARACTERS, Config.TAG_REGEX);

        if (!name.matches(Config.NAME_REGEX))
            return ChatUtil.send(sender, Messages.ERROR_NAME$INCORRECT$CHARACTERS, Config.NAME_REGEX);

        if (NeoAPI.getGuildManager().getGuild(tag) != null || NeoAPI.getGuildManager().getGuild(name) != null)
            return ChatUtil.send(sender, Messages.ERROR_GUILD$ALREADY$EXISTS);

        if (!NeoAPI.getGuildManager().canCreateGuild(sender.getLocation()))
            return ChatUtil.send(sender, Messages.ERROR_GUILD$NEARBY);

        Guild g = NeoAPI.getGuildManager().createGuild(tag, name, sender);

        ChatUtil.send(sender, Messages.INFO_GUILD$CREATED, g.getTag(), g.getName());

        return ChatUtil.send(sender, Messages.BROADCAST_GUILD$CREATED, g.getTag(), g.getName(), sender.getName());
    }
}
