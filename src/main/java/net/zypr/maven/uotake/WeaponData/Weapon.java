package net.zypr.maven.uotake.WeaponData;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Weapon {


    public static boolean isCategory(String category) {
        return (Objects.equals(category, "main") || Objects.equals(category, "sub") || Objects.equals(category, "grenade") || Objects.equals(category, "food"));
    }
    public static int getAmount(String id, String category) {
        if (Uotake.config.isSet("weapon." + category + "." + id + ".amount")) {
            return Uotake.config.getInt("weapon." + category + "." + id + ".amount");
        }
        return 1;
    }

    public static List<String> getDescription(String id, String category) {
        return Uotake.config.getStringList("weapon." + category + "." + id + ".description");
    }

    public static String getName(String id, String category) {
        return Uotake.config.getString("weapon." + category + "." + id + ".name");
    }

    public static Material getMaterial(String id, String category) {
        if (Uotake.config.isSet("weapon." + category + "." + id)) {
            return Material.getMaterial(String.valueOf(Uotake.config.get("weapon." + category + "." + id + ".item")));
        }
        return Material.AIR;
    }

    public static Integer getCmd(String id, String category) {
        if (Uotake.config.isSet("weapon." + category + "." + id)) {
            return Uotake.config.getInt("weapon." + category + "." + id + ".cmd");
        }
        return 0;
    }

    public static boolean ifExists (String id) {
        return !(Objects.equals(getCategory(id), "null"));
    }
    public static boolean ifExists (String id, String category) {
        return Uotake.config.isSet("weapon." + category + "." + id);
    }


    public static String getCategory (String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.isSet("weapon." + s + "." + id)) {
                return s;
            }
        }
        return "null";
    }

    public static boolean ifPlayerHasWeapon(Player p, String id) {
        if (!ifExists(id)) {return false;}
        String category = getCategory(id);
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        if (Objects.equals(category, "main")) {
            return playerData.getMainWeapons().contains(id);
        } else if (Objects.equals(category, "sub")) {
            return playerData.getSubWeapons().contains(id);
        } else if (Objects.equals(category, "grenade")) {
            return playerData.getGrenades().contains(id);
        } else if (Objects.equals(category, "food")) {
            return playerData.getFoods().contains(id);
        }
        return false;
    }

    public static Integer getCost(String id) {
        if (!ifExists(id)) {return -1;}
        String category = getCategory(id);
        if (Uotake.config.contains("weapon." + category + "." + id + ".cost")) {
            return Uotake.config.getInt("weapon." + category + "." + id + ".cost");
        }
        return -1;
    }

    public static boolean giveWeapon (Player p, String id) {
        if (!ifExists(id) || ifPlayerHasWeapon(p, id)) {return false;}
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        String category = getCategory(id);
        if (Objects.equals(category, "main")) {
            playerData.getMainWeapons().add(id);
        } else if (Objects.equals(category, "sub")) {
            playerData.getSubWeapons().add(id);
        } else if (Objects.equals(category, "grenade")) {
            playerData.getGrenades().add(id);
        } else if (Objects.equals(category, "food")) {
            playerData.getFoods().add(id);
        }
        return true;
    }

    //If weapon is not exist, return 3; If player has weapon already, return 2; If player has not enought money, return 1; If success, return 0; Something error happened, return 4;
    public static Integer buyWeapon(Player p, String id) {
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        if (!ifExists(id)) {return 3;}
        if (ifPlayerHasWeapon(p,id)) {return 2;}
        Integer cost = getCost(id);
        if (playerData.getMoney() >= cost && cost != -1) {
            if(giveWeapon(p,id)) {
                playerData.setMoney(playerData.getMoney() - cost);
                return 0;
            } else {
                return 4;
            }
        }
        return 1;
    }

}