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
    private final HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    public void loadPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        File file = new File(Uotake.getRoot() + "/users/" + uuid + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileConfiguration playerfile = YamlConfiguration.loadConfiguration(file);
        List<String> contents = (List<String>) Uotake.config.getList("load_contents.contents");
        if (contents != null) {
            for (String index : contents) {
                if (!playerfile.isSet(index)) {
                    Object value = Uotake.config.get("load_contents.settings." + index);
                    playerfile.set(index, value);
                    player.sendMessage(index + "を追加しました。: " + value);
                }
            }
        }
        try {
            playerfile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            player.sendMessage("読み込みました");

            // プレイヤーデータの読み込み
            int rank = playerfile.getInt("user.rank");
            int money = playerfile.getInt("user.money");

            List<String> mainWeapons = playerfile.getStringList("weapons.main");
            List<String> subWeapons = playerfile.getStringList("weapons.sub");
            List<String> grenades = playerfile.getStringList("weapons.grenade");
            List<String> foods = playerfile.getStringList("weapons.armor.food");
            List<String> head = playerfile.getStringList("weapons.armor.head");
            List<String> body = playerfile.getStringList("weapons.armor.body");
            List<String> legs = playerfile.getStringList("weapons.armor.legs");
            List<String> foot = playerfile.getStringList("weapons.armor.foot");

            // equipmentセクションの読み込み
            String select = playerfile.getString("equipment.select");

            Map<String, Map<String, String>> equipment = new HashMap<>();
            ConfigurationSection equipmentSection = playerfile.getConfigurationSection("equipment");

            if (equipmentSection != null) {
                for (String key : equipmentSection.getKeys(false)) {
                    if (Objects.equals(key, "select")) {
                        continue;
                    }
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
            BattleStatus battleStatus = new BattleStatus(0, 0, 0, 0, 0, 0);
            battleStatus.setWins(playerfile.getInt("battle_status.wins"));
            battleStatus.setLosses(playerfile.getInt("battle_status.losses"));
            battleStatus.setDraws(playerfile.getInt("battle_status.draws"));
            battleStatus.setKills(playerfile.getInt("battle_status.kills"));
            battleStatus.setDeaths(playerfile.getInt("battle_status.deaths"));
            battleStatus.setBonusPoints(playerfile.getInt("battle_status.bonuspoint"));

            boolean bloodSetting = playerfile.getBoolean("setting.blood");

            // プレイヤーデータを作成
            PlayerData data = new PlayerData(rank, money, mainWeapons, subWeapons, grenades, foods, head, body, legs, foot, equipment, select, battleStatus, bloodSetting);
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

        FileConfiguration playerfile = YamlConfiguration.loadConfiguration(file);
        playerfile.set("user.rank", data.getRank());
        playerfile.set("user.money", data.getMoney());
        playerfile.set("weapons.main", data.getMainWeapons());
        playerfile.set("weapons.sub", data.getSubWeapons());
        playerfile.set("weapons.grenade", data.getGrenades());
        playerfile.set("weapons.food", data.getFoods());

        // equipmentの保存
        playerfile.set("equipment.select", data.getSelect());
        for (String key : data.getEquipment().keySet()) {
            Map<String, String> equipment = data.getEquipment().get(key);
            playerfile.createSection("equipment." + key, equipment);
        }

        // battle_statusの保存
        BattleStatus battleStatus = data.getBattleStatus();
        playerfile.set("battle_status.wins", battleStatus.getWins());
        playerfile.set("battle_status.losses", battleStatus.getLosses());
        playerfile.set("battle_status.draws", battleStatus.getDraws());
        playerfile.set("battle_status.kills", battleStatus.getKills());
        playerfile.set("battle_status.deaths", battleStatus.getDeaths());
        playerfile.set("battle_status.bonuspoint", battleStatus.getBonusPoints());

        playerfile.set("setting.blood", data.isBloodSetting());

        try {
            playerfile.save(file);
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
