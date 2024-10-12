package net.zypr.maven.uotake.WeaponData;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class WeaponByType {
    private final List<String> WEAPONS_AR = new ArrayList<>();
    private final List<String> WEAPONS_SG = new ArrayList<>();
    private final List<String> WEAPONS_SR = new ArrayList<>();
    private final List<String> WEAPONS_SMG = new ArrayList<>();
    private final List<String> WEAPONS_RL = new ArrayList<>();
    private final List<String> WEAPONS_HG = new ArrayList<>();

    public WeaponByType() {
        ConfigurationSection categories = Uotake.config.getConfigurationSection("weapon");
        if (categories != null) {
            for (String key : categories.getKeys(false)) { //key : main, sub, grenade, foods
                ConfigurationSection weapons = Uotake.config.getConfigurationSection("weapon." + key);
                if (weapons != null) {
                    for (String weapon : weapons.getKeys(false)) {
                        String type = String.valueOf(Uotake.config.get("weapon." + key + "." + weapon + ".type"));
                        switch (type) {
                            case "ar":
                                WEAPONS_AR.add(weapon);
                                break;
                            case "sg":
                                WEAPONS_SG.add(weapon);
                                break;
                            case "sr":
                                WEAPONS_SR.add(weapon);
                                break;
                            case "smg":
                                WEAPONS_SMG.add(weapon);
                                break;
                            case "rl":
                                WEAPONS_RL.add(weapon);
                                break;
                            case "hg":
                                WEAPONS_HG.add(weapon);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    public List<String> getWeapons(String type) {
        switch (type) {
            case "ar":
                return WEAPONS_AR;
            case "sg":
                return WEAPONS_SG;
            case "sr":
                return WEAPONS_SR;
            case "smg":
                return WEAPONS_SMG;
            case "rl":
                return WEAPONS_RL;
            case "hg":
                return WEAPONS_HG;
            default:
                return null;

        }
    }
}