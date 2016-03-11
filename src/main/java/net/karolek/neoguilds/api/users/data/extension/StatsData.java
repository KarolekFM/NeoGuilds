package net.karolek.neoguilds.api.users.data.extension;

import net.karolek.neoguilds.api.users.data.UserData;

public interface StatsData extends UserData {

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
