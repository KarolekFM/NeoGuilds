package net.karolek.neoguilds.impl.users.data;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.NeoConfig;
import net.karolek.neoguilds.api.NeoAPI;
import net.karolek.neoguilds.api.data.AbstractData;
import net.karolek.neoguilds.api.users.User;
import net.karolek.neoguilds.api.users.data.StatsData;
import net.karolek.store.Queries;
import net.karolek.store.common.QueryCallback;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class StatsDataImpl extends AbstractData<User> implements StatsData {

    private int points = NeoConfig.RANKING_START$POINTS;
    private int kills = 0;
    private int deaths = 0;

    public StatsDataImpl(User user) {
        super(user);
    }

    @Override
    public void incrementPoints(int points) {
        this.points += points;
    }

    @Override
    public void decrementPoints(int points) {
        this.points -= points;
    }

    @Override
    public void incrementKill() {
        this.kills++;
    }

    @Override
    public void decrementKill() {
        this.kills--;
    }


    @Override
    public void incrementDeath() {
        this.deaths++;
    }

    @Override
    public void decrementDeath() {
        this.deaths--;
    }

    @Override
    public void loadData() {
        Queries.customQuery().query("SELECT `points`, `kills`, `deaths` FROM `" + NeoConfig.MYSQL_PREFIX + "stats` WHERE `uuid`='" + getT().getUUID() + "'").callback(new QueryCallback() {
            @Override
            public void done(ResultSet resultSet) throws SQLException {
                if (resultSet != null) {
                    setPoints(resultSet.getInt("points"));
                    setKills(resultSet.getInt("kills"));
                    setDeaths(resultSet.getInt("deaths"));
                }
            }

            @Override
            public void error(Throwable throwable) {
            }
        }).execute(NeoAPI.getStore());
    }

    @Override
    public void saveData() {
        Queries.customQuery().query(
                "INSERT INTO `" + NeoConfig.MYSQL_PREFIX + "stats`(`id`, `uuid`, `points`, `kills`, `deaths`) VALUES (NULL,'" + getT().getUUID() + "'," + getPoints() + "," + getKills() + "," + getDeaths() + ") " +
                        "ON DUPLICATE KEY UPDATE `points`=" + getPoints() + ",`kills`=" + getKills() + ",`deaths`=" + getDeaths() + ";"
        ).execute(NeoAPI.getStore());
    }
}
