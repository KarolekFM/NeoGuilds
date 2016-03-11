package net.karolek.neoguilds.commands.exceptions;


import net.karolek.neoguilds.NeoLang;
import net.karolek.neoguilds.commands.AbstractCommand;

public class PermissionException extends CommandException {
    public PermissionException(String message) {
        super(NeoLang.COMMANDS_NO$PERMISSIONS.getMessage(message));
    }

    public PermissionException(AbstractCommand executor) {
        this(executor.getPermission());
    }
}
