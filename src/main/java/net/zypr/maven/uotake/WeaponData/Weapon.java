package net.zypr.maven.uotake.WeaponData;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weapon {

    private static final List<String> CATEGORIES = Arrays.asList("main", "sub", "grenade", "food", "head", "body", "legs", "foot");

    public static boolean isCategory(String category) {
        return CATEGORIES.contains(category);
    }

    public static int getAmount(String id, String category) {
        return Uotake.config.getInt("weapon." + category + "." + id + ".amount", 1);
    }

    public static List<String> getDescription(String id, String category) {
        return Uotake.config.getStringList("weapon." + category + "." + id + ".description");
    }

    public static String getName(String id, String category) {
        return Uotake.config.getString("weapon." + category + "." + id + ".name");
    }

    public static Material getMaterial(String id, String category) {
        return Material.getMaterial(Uotake.config.getString("weapon." + category + "." + id + ".item", "AIR"));
    }

    public static Integer getCmd(String id, String category) {
        return Uotake.config.getInt("weapon." + category + "." + id + ".cmd", 0);
    }

    public static boolean ifExists(String id) {
        return !getCategory(id).equals("null");
    }

    public static boolean ifExists(String id, String category) {
        return Uotake.config.isSet("weapon." + category + "." + id);
    }

    public static String getCategory(String id) {
        return CATEGORIES.stream()
                .filter(category -> Uotake.config.isSet("weapon." + category + "." + id))
                .findFirst()
                .orElse("null");
    }

    public static boolean ifPlayerHasWeapon(Player p, String id) {
        if (!ifExists(id)) {
            return false;
        }
        String category = getCategory(id);
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        return getPlayerWeaponsByCategory(playerData, category).contains(id);
    }

    public static Integer getCost(String id) {
        if (!ifExists(id)) {
            return -1;
        }
        String category = getCategory(id);
        return Uotake.config.getInt("weapon." + category + "." + id + ".cost", -1);
    }

    public static boolean giveWeapon(Player p, String id) {
        if (!ifExists(id) || ifPlayerHasWeapon(p, id)) {
            return false;
        }
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        String category = getCategory(id);
        getPlayerWeaponsByCategory(playerData, category).add(id);
        return true;
    }

    public static Integer buyWeapon(Player p, String id) {
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        if (!ifExists(id)) {
            return 3;
        }
        if (ifPlayerHasWeapon(p, id)) {
            return 2;
        }
        Integer cost = getCost(id);
        if (playerData.getMoney() >= cost && cost != -1) {
            if (giveWeapon(p, id)) {
                playerData.setMoney(playerData.getMoney() - cost);
                return 0;
            } else {
                return 4;
            }
        }
        return 1;
    }

    private static List<String> getPlayerWeaponsByCategory(PlayerData playerData, String category) {
        switch (category) {
            case "main":
                return playerData.getMainWeapons();
            case "sub":
                return playerData.getSubWeapons();
            case "grenade":
                return playerData.getGrenades();
            case "food":
                return playerData.getFoods();
            case "head":
                return playerData.getHead();
            case "body":
                return playerData.getBody();
            case "legs":
                return playerData.getLegs();
            case "foot":
                return playerData.getFoot();
            default:
                return new ArrayList<>();
        }
    }
}