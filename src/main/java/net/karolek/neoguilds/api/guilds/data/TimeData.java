package net.karolek.neoguilds.api.guilds.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.guilds.Guild;

public interface TimeData extends Data<Guild> {

    long getCreateTime();

    long getLastExplodeTime();

    void setExplodeTime(long time);

}
