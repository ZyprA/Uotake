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
    private HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File file = new File(Uotake.getRoot() + "/users/" + uuid + ".yml");

        if (file.exists()) {
            player.sendMessage("読み込みました");
            // ファイルがある場合は読み込み
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            // プレイヤーデータの読み込み
            int rank = config.getInt("user.rank");
            int money = config.getInt("user.money");

            List<String> mainWeapons = config.getStringList("weapons.main");
            List<String> subWeapons = config.getStringList("weapons.sub");
            List<String> grenades = config.getStringList("weapons.grenade");
            List<String> foods = config.getStringList("weapons.food");

            // equipmentセクションの読み込み
            String select = config.getString("equipment.select");

            Map<String, Map<String, String>> equipment = new HashMap<>();
            ConfigurationSection equipmentSection = config.getConfigurationSection("equipment");

            if (equipmentSection != null) {
                for (String key : equipmentSection.getKeys(false)) {
                    if (Objects.equals(key, "select")) {continue;}
                    Map<String, Object> equipmentObjectMap = equipmentSection.getConfigurationSection(key).getValues(false);
                    Map<String, String> equipmentStringMap = new HashMap<>();

                    for (Map.Entry<String, Object> entry : equipmentObjectMap.entrySet()) {
                        // オブジェクトを文字列に変換して Map に格納
                        equipmentStringMap.put(entry.getKey(), entry.getValue().toString());
                    }
                    equipment.put(key, equipmentStringMap);
                }
            }

            // battle_statusセクションの読み込み
            BattleStatus battleStatus = new BattleStatus(0,0,0,0,0,0);
            battleStatus.setWins(config.getInt("battle_status.wins"));
            battleStatus.setLosses(config.getInt("battle_status.losses"));
            battleStatus.setDraws(config.getInt("battle_status.draws"));
            battleStatus.setKills(config.getInt("battle_status.kills"));
            battleStatus.setDeaths(config.getInt("battle_status.deaths"));
            battleStatus.setBonusPoints(config.getInt("battle_status.bonuspoint"));

            boolean bloodSetting = config.getBoolean("setting.blood");

            // プレイヤーデータを作成
            PlayerData data = new PlayerData(rank, money, mainWeapons, subWeapons, grenades, foods, equipment, select, battleStatus, bloodSetting);
            playerDataMap.put(uuid, data);
        }
    }

    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerData data = playerDataMap.get(uuid);
        File file = new File(Uotake.getRoot() + "/users/" + uuid + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("user.rank", data.getRank());
        config.set("user.money", data.getMoney());
        config.set("weapons.main", data.getMainWeapons());
        config.set("weapons.sub", data.getSubWeapons());
        config.set("weapons.grenade", data.getGrenades());
        config.set("weapons.food", data.getFoods());

        // equipmentの保存
        config.set("equipment.select", data.getSelect());
        for (String key : data.getEquipment().keySet()) {
            Map<String, String> equipment = data.getEquipment().get(key);
            config.createSection("equipment." + key, equipment);
        }

        // battle_statusの保存
        BattleStatus battleStatus = data.getBattleStatus();
        config.set("battle_status.wins", battleStatus.getWins());
        config.set("battle_status.losses", battleStatus.getLosses());
        config.set("battle_status.draws", battleStatus.getDraws());
        config.set("battle_status.kills", battleStatus.getKills());
        config.set("battle_status.deaths", battleStatus.getDeaths());
        config.set("battle_status.bonuspoint", battleStatus.getBonusPoints());

        config.set("setting.blood", data.isBloodSetting());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public PlayerData getPlayerData(UUID uniqueId) {
        return playerDataMap.get(uniqueId);
    }

    // 他のメソッド
}
