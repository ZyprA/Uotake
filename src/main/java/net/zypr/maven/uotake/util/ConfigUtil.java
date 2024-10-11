package net.zypr.maven.uotake.util;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtil {
    private File file;
    private FileConfiguration config;

    public ConfigUtil(String path) {
        this.file = new File(Uotake.getRoot() + path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean save() {
        try {
            this.config.save(this.file);
            return true;
        }catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}

