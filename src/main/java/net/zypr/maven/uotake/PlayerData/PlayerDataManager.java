package net.zypr.maven.uotake.PlayerData;

import net.zypr.maven.uotake.EquipmentData.ArmorData.Armor;
import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorType;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static net.zypr.maven.uotake.Uotake.armorLoader;
import static net.zypr.maven.uotake.Uotake.weaponLoader;

public class PlayerDataManager {
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File file = getPlayerDataFile(uuid);
        createFileIfNotExists(file);

        FileConfiguration playerFile = YamlConfiguration.loadConfiguration(file);
        loadDefaultContents(playerFile, player);
        saveFile(playerFile, file);

        if (file.exists()) {
            player.sendMessage("読み込みました");
            PlayerData data = loadPlayerDataFromFile(playerFile);
            playerDataMap.put(uuid, data);
        }
    }

    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerData data = playerDataMap.get(uuid);
        File file = getPlayerDataFile(uuid);
        createFileIfNotExists(file);

        FileConfiguration playerFile = YamlConfiguration.loadConfiguration(file);
        savePlayerDataToFile(playerFile, data);
        saveFile(playerFile, file);
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public PlayerData getPlayerData(UUID uniqueId) {
        return playerDataMap.get(uniqueId);
    }

    private File getPlayerDataFile(UUID uuid) {
        return new File(Uotake.getRoot() + "/users/" + uuid + ".yml");
    }

    private void createFileIfNotExists(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDefaultContents(FileConfiguration playerFile, Player player) {
        List<String> contents = (List<String>) Uotake.config.getList("load_contents.contents");
        if (contents != null) {
            for (String index : contents) {
                if (!playerFile.isSet(index)) {
                    Object value = Uotake.config.get("load_contents.settings." + index);
                    playerFile.set(index, value);
                    player.sendMessage(index + "を追加しました。: " + value);
                }
            }
        }
    }

    private PlayerData loadPlayerDataFromFile(FileConfiguration playerFile) {
        int rank = playerFile.getInt("user.rank");
        int money = playerFile.getInt("user.money");

        //　所持品
        List<Weapon> mainWeapons = getWeaponsFromConfig(playerFile, "weapons.main");
        List<Weapon> subWeapons = getWeaponsFromConfig(playerFile, "weapons.sub");
        List<Weapon> grenades = getWeaponsFromConfig(playerFile, "weapons.grenade");
        List<Weapon> foods = getWeaponsFromConfig(playerFile, "weapons.food");
        List<Armor> head = getArmorFromConfig(playerFile, "armor.head");
        List<Armor> body = getArmorFromConfig(playerFile, "armor.body");
        List<Armor> legs = getArmorFromConfig(playerFile, "armor.legs");
        List<Armor> foot = getArmorFromConfig(playerFile, "armor.foot");

        // 装備中のものを示す
        String select = playerFile.getString("equipment.select");
        Boolean armorboolean = playerFile.getBoolean("equipment.armor_boolean");

        Map<String, Map<WeaponCategory, Weapon>> equipWeapons = loadEquipWeapons(playerFile);
        Map<ArmorType, Armor> equipArmors = loadEquipArmors(playerFile);

        BattleStatus battleStatus = loadBattleStatus(playerFile);
        boolean bloodSetting = playerFile.getBoolean("setting.blood");

        return new PlayerData(rank, money, mainWeapons, subWeapons, grenades, foods, head, body, legs, foot, equipWeapons, equipArmors, select, armorboolean, battleStatus, bloodSetting);
    }

    private BattleStatus loadBattleStatus(FileConfiguration playerFile) {
        BattleStatus battleStatus = new BattleStatus(0, 0, 0, 0, 0, 0);
        battleStatus.setWins(playerFile.getInt("battle_status.wins"));
        battleStatus.setLosses(playerFile.getInt("battle_status.losses"));
        battleStatus.setDraws(playerFile.getInt("battle_status.draws"));
        battleStatus.setKills(playerFile.getInt("battle_status.kills"));
        battleStatus.setDeaths(playerFile.getInt("battle_status.deaths"));
        battleStatus.setBonusPoints(playerFile.getInt("battle_status.bonuspoint"));
        return battleStatus;
    }

    private void savePlayerDataToFile(FileConfiguration playerFile, PlayerData data) {
        playerFile.set("user.rank", data.getRank());
        playerFile.set("user.money", data.getMoney());
        playerFile.set("weapons.main", data.getMainWeapons().stream().map(Weapon::getId).collect(Collectors.toList()));
        playerFile.set("weapons.sub", data.getMainWeapons().stream().map(Weapon::getId).collect(Collectors.toList()));
        playerFile.set("weapons.grenade", data.getMainWeapons().stream().map(Weapon::getId).collect(Collectors.toList()));
        playerFile.set("weapons.food", data.getMainWeapons().stream().map(Weapon::getId).collect(Collectors.toList()));

        playerFile.set("equipment.select", data.getSelect());
        for (String key : data.getEquipment().keySet()) {
            Map<WeaponCategory, Weapon> equipment = data.getEquipment().get(key);
            playerFile.createSection("equipment." + key, equipment.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getId())));
        }

        BattleStatus battleStatus = data.getBattleStatus();
        playerFile.set("battle_status.wins", battleStatus.getWins());
        playerFile.set("battle_status.losses", battleStatus.getLosses());
        playerFile.set("battle_status.draws", battleStatus.getDraws());
        playerFile.set("battle_status.kills", battleStatus.getKills());
        playerFile.set("battle_status.deaths", battleStatus.getDeaths());
        playerFile.set("battle_status.bonuspoint", battleStatus.getBonusPoints());

        playerFile.set("setting.blood", data.isBloodSetting());
    }

    private void saveFile(FileConfiguration playerFile, File file) {
        try {
            playerFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Armor> getArmorFromConfig(FileConfiguration playerFile, String path) {
        List<String> armorNames = playerFile.getStringList(path);
        List<Armor> armors = new ArrayList<>();
        for (String armorName : armorNames) {
            Armor armor = armorLoader.getArmorByName(armorName); // Assuming a method to get Armor by name
            if (armor != null) {
                armors.add(armor);
            }
        }
        return armors;
    }

    private List<Weapon> getWeaponsFromConfig(FileConfiguration playerFile, String path) {
        List<String> weaponNames = playerFile.getStringList(path);
        List<Weapon> weapons = new ArrayList<>();
        for (String weaponName : weaponNames) {
            Weapon weapon = weaponLoader.getWeaponByName(weaponName); // Assuming a method to get Weapon by name
            if (weapon != null) {
                weapons.add(weapon);
            }
        }
        return weapons;
    }

    private Map<String, Map<WeaponCategory, Weapon>> loadEquipWeapons(FileConfiguration playerFile) {
        Map<String, Map<WeaponCategory, Weapon>> equipment = new HashMap<>();
        ConfigurationSection equipmentSection = playerFile.getConfigurationSection("equipment.weapon");

        if (equipmentSection != null) {
            for (String key : equipmentSection.getKeys(false)) {
                if (!Objects.equals(key, "a") && !Objects.equals(key, "b")) {
                    continue;
                }
                Map<String, Object> equipmentObjectMap = equipmentSection.getConfigurationSection(key).getValues(false);
                Map<WeaponCategory, Weapon> equipmentWeaponMap = new HashMap<>();

                for (Map.Entry<String, Object> entry : equipmentObjectMap.entrySet()) {
                    Weapon weapon = weaponLoader.getWeaponByName(entry.getValue().toString());
                    if (weapon != null) {
                        equipmentWeaponMap.put(WeaponCategory.valueOf(entry.getKey().toUpperCase()), weapon);
                    }
                }
                equipment.put(key, equipmentWeaponMap);
            }
        }
        return equipment;
    }

    private Map<ArmorType, Armor> loadEquipArmors(FileConfiguration playerFile) {
        Map<ArmorType, Armor> equipment = new HashMap<>();
        ConfigurationSection equipmentSection = playerFile.getConfigurationSection("equipment.armor");

        if (equipmentSection != null) {
            for (String key : equipmentSection.getKeys(false)) {
                Armor armor = armorLoader.getArmorByName(equipmentSection.getString(key));
                if (armor != null) {
                    equipment.put(ArmorType.valueOf(key.toUpperCase()), armor);
                }
            }
        }
        return equipment;
    }
}
