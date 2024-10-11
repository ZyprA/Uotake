package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class Weapon {
    public static String getName(String id) {
        List<String> exList = Arrays.asList("main", "sub", "grenade", "food");
        for (String s : exList) {
            if (Uotake.config.contains("weapon." + s + "." + id)) {
                return String.valueOf(Uotake.config.get("weapon." + s + "." + id + ".name"));
            }
        }
        return "null";
        
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

}
