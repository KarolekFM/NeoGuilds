package net.karolek.neoguilds.api.guilds.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.guilds.Guild;

public interface OtherData extends Data<Guild> {

    boolean isPreDeleted();

    void setPreDeleted(boolean preDeletd);

}
