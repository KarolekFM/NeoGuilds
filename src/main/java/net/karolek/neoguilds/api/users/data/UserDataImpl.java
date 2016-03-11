package net.karolek.neoguilds.api.users.data;

import lombok.Getter;
import net.karolek.neoguilds.api.users.User;

@Getter
public abstract class UserDataImpl implements UserData {

    protected final User user;

    public UserDataImpl(User user) {
        this.user = user;
        loadData();
    }

}
