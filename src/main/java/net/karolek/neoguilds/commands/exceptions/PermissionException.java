package net.karolek.neoguilds.commands.exceptions;


import net.karolek.neoguilds.Messages;
import net.karolek.neoguilds.commands.AbstractCommand;
import net.karolek.neoguilds.utils.ChatUtil;

public class PermissionException extends CommandException {
    public PermissionException(String message) {
        super(ChatUtil.getMessage(Messages.COMMANDS_NO$PERMISSIONS, message));
    }

    public PermissionException(AbstractCommand executor) {
        this(executor.getPermission());
    }
}
