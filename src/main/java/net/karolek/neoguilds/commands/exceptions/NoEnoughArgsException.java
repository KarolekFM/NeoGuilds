package net.karolek.neoguilds.commands.exceptions;


import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.commands.AbstractCommand;
import net.karolek.neoguilds.utils.ChatUtil;

public class NoEnoughArgsException extends CommandException {

    public NoEnoughArgsException(String message) {
        super(ChatUtil.getMessage(Messages.COMMANDS_NO$ENOUGH$ARGS, message));
    }

    public NoEnoughArgsException(AbstractCommand executor) {
        this(executor.getUsage());
    }
}
