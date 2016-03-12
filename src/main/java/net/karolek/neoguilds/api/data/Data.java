package net.karolek.neoguilds.api.data;

public interface Data<T extends DataExtension> {

    void loadData();

    void saveData();

}
