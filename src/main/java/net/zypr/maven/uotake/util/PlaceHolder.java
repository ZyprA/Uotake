package net.zypr.maven.uotake.util;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;


public class PlaceHolder {
    public static String r(String text, Player p, String prefix) {
        String result = text;
        if (Objects.equals(prefix, "name")) {
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
                        result = result.replace("{equip_now_" + index + "_name}", Weapon.getName(equipment.get(select).get(index),index));
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_name}", Weapon.getName(equipment.get(key).get(index),index));
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
                        result = result.replace("{equip_now_" + index + "_item}",Weapon.getMaterial(equipment.get(select).get(index),index).toString());
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_item}", Weapon.getMaterial(equipment.get(key).get(index),index).toString());
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
                        result = result.replace("{equip_now_" + index + "_cmd}",Weapon.getCmd(equipment.get(select).get(index),index).toString());
                    } else {
                        result = result.replace("{equip_" + key + "_" + index + "_cmd}", Weapon.getCmd(equipment.get(key).get(index),index).toString());
                    }
                }
            }
        }
        return result;
    }
}
