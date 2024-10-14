package net.zypr.maven.uotake;

import net.zypr.maven.uotake.PlayerData.PlayerDataManager;
import net.zypr.maven.uotake.Others.CommandRegister;
import net.zypr.maven.uotake.Others.Scoreboard;
import net.zypr.maven.uotake.WeaponData.WeaponByType;
import net.zypr.maven.uotake.events.InventoryClick;
import net.zypr.maven.uotake.events.JoinEvent;
import net.zypr.maven.uotake.events.NPCClick;
import net.zypr.maven.uotake.events.OnClickedItem;
import net.zypr.maven.uotake.util.ConfigUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;

import java.io.File;

public class Uotake extends JavaPlugin {

    private static Plugin plugin;
    public static FileConfiguration config;
    public static FileConfiguration menu;
    public static FileConfiguration inventory;
    public static ConfigUtil variable;
    public static PlayerDataManager playerDataManager;
    public static WeaponByType weaponbytype;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("UGO Plugin is enabled.");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnClickedItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new NPCClick(), this);

        CommandRegister.load();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        plugin = this;
        config = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/config.yml"));
        menu = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/lobby/menu.yml"));
        inventory = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/lobby/inventory.yml"));
        variable = new ConfigUtil("/setting/lobby/variable.yml");
        playerDataManager = new PlayerDataManager();
        new Scoreboard().showScoreboard();
        weaponbytype = new WeaponByType();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("UGO Plugin is disabled.");
    }

    public static String getRoot() {
        return "..";
    }
    public static Plugin getPlugin() {
        return plugin;
    }
}
