package net.zypr.maven.uotake;

import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorLoader;
import net.zypr.maven.uotake.EquipmentData.SkillData.SkillLoader;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponLoader;
import net.zypr.maven.uotake.Others.CommandRegister;
import net.zypr.maven.uotake.Others.Scoreboard;
import net.zypr.maven.uotake.PlayerData.PlayerDataManager;
import net.zypr.maven.uotake.events.*;
import net.zypr.maven.uotake.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Uotake extends JavaPlugin {

    public static FileConfiguration config;
    public static FileConfiguration menu;
    public static FileConfiguration inventory;
    public static ConfigUtil variable;
    public static PlayerDataManager playerDataManager;
    public static WeaponLoader weaponLoader;
    public static ArmorLoader armorLoader;
    public static SkillLoader skillLoader;
    private static Plugin plugin;

    public static String getRoot() {
        return "..";
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("UGO Plugin is enabled.");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new OnClickedItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new NPCClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);

        CommandRegister.load();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        plugin = this;
        config = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/config.yml"));
        menu = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/lobby/menu.yml"));
        inventory = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/lobby/inventory.yml"));
        variable = new ConfigUtil("/setting/lobby/variable.yml");
        playerDataManager = new PlayerDataManager();
        weaponLoader = new WeaponLoader(config);
        weaponLoader.loadWeapons();

        armorLoader = new ArmorLoader(config);
        armorLoader.loadArmors();

        skillLoader = new SkillLoader(config);
        skillLoader.loadSkills();

        new Scoreboard().showScoreboard();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("UGO Plugin is disabled.");
    }
}
