package net.karolek.neoguilds.impl.guilds.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.guilds.Guild;
import net.karolek.neoguilds.api.guilds.data.OtherData;

@Getter
@Setter
public class OtherDataImpl extends AbstractData<Guild> implements OtherData {

    private boolean preDeleted = false;

    public OtherDataImpl(Guild guild) {
        super(guild);
    }

    @Override
    public void loadData() {
    }

    @Override
    public void saveData() {
    }

}
