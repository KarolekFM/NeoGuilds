package net.karolek.neoguilds.configuration;

import lombok.Getter;
import lombok.Setter;
import net.karolek.neoguilds.configuration.annotations.Comment;
import net.karolek.neoguilds.configuration.annotations.Ignore;
import net.karolek.neoguilds.configuration.fields.ConfigField;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

@Getter
@Setter
public class Configuration {

    protected final JavaPlugin plugin;
    protected final String fileName;
    protected final String prefix;

    private File configFile;
    private CustomConfiguration fileConfiguration;

    public Configuration(JavaPlugin plugin, String fileName, String prefix) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.prefix = prefix;
        reloadConfiguration();
    }

    private static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected File getConfigFile() {
        if (configFile == null) {
            configFile = new File(getPlugin().getDataFolder(), fileName);
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                InputStream resource = getPlugin().getResource(fileName);
                if (resource != null)
                    copy(resource, configFile);
            }
        }
        return configFile;
    }

    protected CustomConfiguration getFileConfiguration() {
        if (fileConfiguration == null)
            fileConfiguration = CustomConfiguration.loadConfiguration(getConfigFile());
        return fileConfiguration;
    }

    public void loadConfiguration() {
        getPlugin().getLogger().log(Level.INFO, "Loading '" + fileName + "'!");
        try {
            CustomConfiguration f = getFileConfiguration();
            for (Field field : getClass().getFields()) {
                if ((Modifier.isStatic(field.getModifiers())) && (Modifier.isPublic(field.getModifiers()))) {
                    String path = prefix + field.getName().toLowerCase().replace("$", "-").replace("_", ".");
                    if (f.isSet(path)) {

                        if (field.getType().getSuperclass().equals(ConfigField.class)) {
                            field.set(null, field.getType().getConstructors()[0].newInstance(f.get(path)));
                        } else {
                            field.set(null, f.get(path));
                        }
                    }
                }
            }
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, "An error occured while loading '" + fileName + "'!", e);
        }
    }

    public void saveConfiguration() {
        getPlugin().getLogger().log(Level.INFO, "Saving '" + fileName + "'!");
        try {
            CustomConfiguration f = getFileConfiguration();
            for (String key : f.getKeys(false)) { //TODO change this shit :v
                f.set(key, null);
            }
            for (Field field : getClass().getFields()) {
                if ((Modifier.isStatic(field.getModifiers())) && (Modifier.isPublic(field.getModifiers()))) {
                    if (field.isAnnotationPresent(Ignore.class)) continue;
                    String path = prefix + field.getName().toLowerCase().replace("$", "-").replace("_", ".");
                    if (field.isAnnotationPresent(Comment.class)) {
                        Comment comment = field.getAnnotation(Comment.class);
                        f.set(path, field.get(null).toString(), comment.value());
                    } else {
                        f.set(path, field.get(null).toString());
                    }
                }
            }
            getFileConfiguration().save(getConfigFile());
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, "An error occured while saving '" + fileName + "'!", e);
        }
    }

    public void reloadConfiguration() {
        configFile = null;
        fileConfiguration = null;
        loadConfiguration();
        saveConfiguration();
    }

}
