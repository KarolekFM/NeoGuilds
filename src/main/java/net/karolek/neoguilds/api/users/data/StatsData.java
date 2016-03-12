package net.karolek.neoguilds.api.users.data;

import net.karolek.neoguilds.api.data.Data;
import net.karolek.neoguilds.api.users.User;

public interface StatsData extends Data<User> {

    int getPoints();

    void setPoints(int points);

    void incrementPoints(int points);

    void decrementPoints(int points);

    int getKills();

    void setKills(int kills);

    void incrementKill();

    void decrementKill();

    int getDeaths();

    void setDeaths(int deaths);

    void incrementDeath();

    void decrementDeath();

}
