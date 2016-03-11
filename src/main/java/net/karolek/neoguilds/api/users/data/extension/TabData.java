package net.karolek.neoguilds.api.users.data.extension;

public interface TabData {

    void sendTab();

    void setSlot(int row, int column, String text);

    void updateSlot(int row, int column, String text);

    void setHeaderAndFooter(String header, String footer);

}
