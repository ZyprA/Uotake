package net.zypr.maven.uotake.PlayerData;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
        List<String> mainWeapons = playerFile.getStringList("weapons.main");
        List<String> subWeapons = playerFile.getStringList("weapons.sub");
        List<String> grenades = playerFile.getStringList("weapons.grenade");
        List<String> foods = playerFile.getStringList("weapons.armor.food");
        List<String> head = playerFile.getStringList("weapons.armor.head");
        List<String> body = playerFile.getStringList("weapons.armor.body");
        List<String> legs = playerFile.getStringList("weapons.armor.legs");
        List<String> foot = playerFile.getStringList("weapons.armor.foot");

        // 装備中のものを示す
        String select = playerFile.getString("equipment.select");
        Map<String, Map<String, String>> equipment = loadEquipment(playerFile);

        BattleStatus battleStatus = loadBattleStatus(playerFile);
        boolean bloodSetting = playerFile.getBoolean("setting.blood");

        return new PlayerData(rank, money, mainWeapons, subWeapons, grenades, foods, head, body, legs, foot, equipment, select, battleStatus, bloodSetting);
    }

    private Map<String, Map<String, String>> loadEquipment(FileConfiguration playerFile) {
        Map<String, Map<String, String>> equipment = new HashMap<>();
        ConfigurationSection equipmentSection = playerFile.getConfigurationSection("equipment");

        if (equipmentSection != null) {
            for (String key : equipmentSection.getKeys(false)) {
                if (Objects.equals(key, "select")) {
                    continue;
                }
                Map<String, Object> equipmentObjectMap = equipmentSection.getConfigurationSection(key).getValues(false);
                Map<String, String> equipmentStringMap = new HashMap<>();

                for (Map.Entry<String, Object> entry : equipmentObjectMap.entrySet()) {
                    equipmentStringMap.put(entry.getKey(), entry.getValue().toString());
                }
                equipment.put(key, equipmentStringMap);
            }
        }
        return equipment;
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
        playerFile.set("weapons.main", data.getMainWeapons());
        playerFile.set("weapons.sub", data.getSubWeapons());
        playerFile.set("weapons.grenade", data.getGrenades());
        playerFile.set("weapons.food", data.getFoods());

        playerFile.set("equipment.select", data.getSelect());
        for (String key : data.getEquipment().keySet()) {
            Map<String, String> equipment = data.getEquipment().get(key);
            playerFile.createSection("equipment." + key, equipment);
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
}
