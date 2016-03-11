package net.karolek.neoguilds.commands.exceptions;


import net.karolek.neoguilds.NeoLang;
import net.karolek.neoguilds.commands.AbstractCommand;

public class NoEnoughArgsException extends CommandException {

    public NoEnoughArgsException(String message) {
        super(NeoLang.COMMANDS_NO$ENOGUH$ARGS.getMessage(message));
    }

    public NoEnoughArgsException(AbstractCommand executor) {
        this(executor.getUsage());
    }
}
