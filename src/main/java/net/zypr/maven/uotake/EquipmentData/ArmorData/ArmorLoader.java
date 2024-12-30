package net.zypr.maven.uotake.EquipmentData.ArmorData;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ArmorLoader {

    private final Map<String, Armor> armorMap = new HashMap<>();
    public void loadArmors(FileConfiguration config) {

        armorMap.clear();
        ConfigurationSection armorSection = config.getConfigurationSection("armor");
        if (armorSection == null) {
            return;
        }

        for (String key : armorSection.getKeys(false)) {
            ConfigurationSection armorData = armorSection.getConfigurationSection(key);

            if (armorData != null) {
                String name = armorData.getString("name");
                int cost = armorData.getInt("cost", -1);
                int tier = armorData.getInt("tier", -1);
                ArmorType type = null;
                try {
                    type = ArmorType.valueOf(armorData.getString("type").toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("無効なtype: " + armorData.getString("type"));
                }

                if (cost != -1 && tier != -1 && type != null) {
                    Armor armor = new Armor();
                    armor.setId(key);
                    armor.setName(name);
                    armor.setCost(cost);
                    armor.setTier(tier);
                    armor.setDescription(armorData.getStringList("description"));
                    armor.setType(type);
                    armor.setCmd(armorData.getInt("cmd"));
                    armor.setMaterial(Material.getMaterial(armorData.getString("item", "AIR")));
                    armorMap.put(key, armor);
                }
            }
        }
    }

    public Armor getArmorByName(String name) {
        return armorMap.get(name);
    }

}
