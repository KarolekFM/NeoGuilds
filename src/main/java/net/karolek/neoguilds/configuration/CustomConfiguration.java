package net.karolek.neoguilds.configuration;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public final class CustomConfiguration extends YamlConfiguration {

    private int commentsCounter = 0;


    public CustomConfiguration() {
    }

    public static CustomConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        CustomConfiguration config = new CustomConfiguration();
        try {
            config.load(file);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, e);
        } catch (InvalidConfigurationException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, e);
        }
        return config;
    }


    public void set(String path, Object value, String... comments) {
        //for (String comment : comments) {
        //    this.set(path + "_COMMENT_" + commentsCounter, comment);
        //    commentsCounter++;
        // }
        this.set(path, value);
    }

    @Override
    public String saveToString() {
        /*String[] lines = super.saveToString().split("\n");

        StringBuilder config = new StringBuilder("");

        for (String line : lines) {

            String l = line;

            int index = l.split(":")[0].lastIndexOf(" ");

            String spaces = "";

            for (int i = 0; i < index; i++) {
                spaces += " ";
            }


            if (l.contains("_COMMENT_")) {
                String comment = l.substring(l.indexOf(":") + 1, l.length());
                comment = comment.replace("'", "");
                config.append(spaces).append("# ").append(comment.trim()).append("\n");
            } else {
                config.append(l).append("\n");
            }


        }

        return config.toString();*/
        return super.saveToString();
    }

}
