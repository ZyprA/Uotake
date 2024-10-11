package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;


public class PlaceHolder {
    public static String r(String text, Player p, String prefix) {
        String result = text;
        if (Objects.equals(prefix, "player")) {
            PlayerData data = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            result = result.replace("{player_name}",p.getName());
            result = result.replace("{player_kill}",String.valueOf(data.getBattleStatus().getKills()));
            result = result.replace("{player_death}",String.valueOf(data.getBattleStatus().getDeaths()));
            result = result.replace("{player_kd}",String.valueOf(data.getBattleStatus().getDeaths() != 0 ? data.getBattleStatus().getKills() / data.getBattleStatus().getDeaths() : 0));
            result = result.replace("{player_rank_prefix}",String.valueOf(Uotake.config.get("rank." + String.valueOf(data.getRank()) + ".prefix")));
            result = result.replace("{player_rank}",String.valueOf(data.getRank()));
            result = result.replace("{player_bp}",String.valueOf(data.getBattleStatus().getBonusPoints()));
            result = result.replace("{player_money}",String.valueOf(data.getMoney()));
            result = result.replace("{player_speed}","0");
            result = result.replace("{player_power}","0");
            result = result.replace("{player_defence}","0");
            result = result.replace("{player_fr}","0");
            result = result.replace("{player_reload}","0");
            result = result.replace("{player_accuracy}","0");
        } else if (Objects.equals(prefix, "name")) {
            PlayerData data = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            String select = data.getSelect();
            Map<String , Map<String, String>> equipment = (data.getEquipment());
            /* INK */
            result = result.replace("{equip_a_ink_name}", (Objects.equals(select, "a")) ? "§a武器セットA-ON" : "§b武器セットA§f-§cOFF");
            result = result.replace("{equip_b_ink_name}", (Objects.equals(select, "b")) ? "§a武器セットB-ON" : "§b武器セットB§f-§cOFF");
            /* EQUIP */
            String[] keys = {"a","b","now"};
            String[] indexes = {"main","sub","grenade","food"};
            for (String key: keys) {
                for (String index : indexes) {
                    if (key.equals("now")) {
                        result = result.replace("{equip_now_" + index + "_name}",Weapon.getName(equipment.get(select).get(index)));
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_name}", Weapon.getName(equipment.get(key).get(index)));
                    }
                }
            }
        } else if (Objects.equals(prefix, "item")) {
            PlayerData data = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            Map<String , Map<String, String>> equipment = (data.getEquipment());
            String select = data.getSelect();
            String[] keys = {"a","b","now"};
            String[] indexes = {"main","sub","grenade","food"};

            for (String key: keys) {
                for (String index : indexes) {
                    if (key.equals("now")) {
                        result = result.replace("{equip_now_" + index + "_item}",Weapon.getMaterial(equipment.get(select).get(index)).toString());
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_item}", Weapon.getMaterial(equipment.get(key).get(index)).toString());
                    }
                }
            }
        } else if (Objects.equals(prefix, "iv")) {
            PlayerData data = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            String select = data.getSelect();
            result = result.replace("{equip_a_ink_iv}", (Objects.equals(select, "a")) ? "10" : "8");
            result = result.replace("{equip_b_ink_iv}", (Objects.equals(select, "b")) ? "10" : "8");
        } else if (Objects.equals(prefix, "cmd")) {
            PlayerData data = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            Map<String , Map<String, String>> equipment = (data.getEquipment());
            String select = data.getSelect();
            String[] keys = {"a","b","now"};
            String[] indexes = {"main","sub","grenade","food"};

            for (String key: keys) {
                for (String index : indexes) {
                    if (key.equals("now")) {
                        result = result.replace("{equip_now_" + index + "_cmd}",Weapon.getCmd(equipment.get(select).get(index)).toString());
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_cmd}", Weapon.getCmd(equipment.get(key).get(index)).toString());
                    }
                }
            }
        }
        return result;
    }
}
