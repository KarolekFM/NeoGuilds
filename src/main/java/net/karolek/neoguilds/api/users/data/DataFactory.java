package net.karolek.neoguilds.api.users.data;

import net.karolek.neoguilds.impl.users.data.UserDataImpl;

import java.util.HashMap;
import java.util.Map;

public final class DataFactory {

    private final Map<Class<? extends UserData>, Class<? extends UserDataImpl>> factories = new HashMap<>();

    public void register(Class<? extends UserData> clazz, Class<? extends UserDataImpl> clazzImpl) {
        factories.put(clazz, clazzImpl);
    }

    public Class<? extends UserDataImpl> getFactory(Class<? extends UserData> clazz) {
        if (!factories.containsKey(clazz))
            throw new IllegalArgumentException("You must register factory for " + clazz.getName());
        return factories.get(clazz);
    }

}
