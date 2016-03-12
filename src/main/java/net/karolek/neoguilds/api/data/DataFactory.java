package net.karolek.neoguilds.api.data;

import java.util.HashMap;
import java.util.Map;

public final class DataFactory {
    private final Map<Class<? extends Data>, Class<? extends AbstractData>> factories = new HashMap();

    public void register(Class<? extends Data> clazz, Class<? extends AbstractData> clazzImpl) {
        this.factories.put(clazz, clazzImpl);
    }

    public Class<? extends AbstractData> getFactory(Class<? extends Data> clazz) {
        if (!this.factories.containsKey(clazz)) {
            throw new IllegalArgumentException("You must register factory for " + clazz.getName());
        }
        return (Class) this.factories.get(clazz);
    }
}
