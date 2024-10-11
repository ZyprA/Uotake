package net.zypr.maven.uotake.WeaponData;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Weapon {
    public static String getName(String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return String.valueOf(Uotake.config.get("weapon." + s + "." + id + ".name"));
            }
        }
        return "!null";
        
    }

    public static Material getMaterial(String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return Material.getMaterial(String.valueOf(Uotake.config.get("weapon." + s + "." + id + ".item")));
            }
        }
        return Material.AIR;
    }

    public static Integer getCmd(String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return Uotake.config.getInt("weapon." + s + "." + id + ".cmd");
            }
        }
        return 0;
    }

    public static boolean ifExists (String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return true;
            }
        }
        return false;
    }

    public static String getType (String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return s;
            }
        }
        return "null";
    }

    public static boolean ifPlayerHasWeapon(Player p, String id) {
        if (!ifExists(id)) {return false;}
        String type = getType(id);
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        if (Objects.equals(type, "main")) {
            return playerData.getMainWeapons().contains(id);
        } else if (Objects.equals(type, "sub")) {
            return playerData.getSubWeapons().contains(id);
        } else if (Objects.equals(type, "grenade")) {
            return playerData.getGrenades().contains(id);
        } else if (Objects.equals(type, "food")) {
            return playerData.getFoods().contains(id);
        }
        return false;
    }

    public static boolean giveWeapon (Player p, String id) {
        if (!ifExists(id) || ifPlayerHasWeapon(p, id)) {return false;}
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        String type = getType(id);
        if (Objects.equals(type, "main")) {
            playerData.getMainWeapons().add(id);
        } else if (Objects.equals(type, "sub")) {
            playerData.getSubWeapons().add(id);
        } else if (Objects.equals(type, "grenade")) {
            playerData.getGrenades().add(id);
        } else if (Objects.equals(type, "food")) {
            playerData.getFoods().add(id);
        }
        return true;
    }

}
