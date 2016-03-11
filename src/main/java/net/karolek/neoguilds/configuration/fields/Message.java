package net.karolek.neoguilds.configuration.fields;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.utils.Util;
import org.bukkit.command.CommandSender;

import java.util.Collection;

@Getter
@Setter
public class Message extends ConfigField<String> {

    private final String message;

    public Message(String s) {
        super(s);
        this.message = s;
    }

    public static Message _(String message) {
        return new Message(message);
    }

    public boolean send(CommandSender sender, Object... replacements) {
        String replace = message;
        for (int i = 0; i < replacements.length; i++)
            replace = replace.replace("{" + i + "}", replacements[i].toString());
        return Util.sendMsg(sender, replace);
    }

    public boolean send(Collection<? extends CommandSender> senders, Object... replacements) {
        String replace = message;
        for (int i = 0; i < replacements.length; i++)
            replace = replace.replace("{" + i + "}", replacements[i].toString());
        return Util.sendMsg(senders, replace);
    }

    public String getMessage(Object... replacements) {
        String replace = message;
        for (int i = 0; i < replacements.length; i++)
            replace = replace.replace("{" + i + "}", replacements[i].toString());
        return Util.fixColor(message);
    }

    @Override
    public String toString() {
        return this.message;
    }

}
