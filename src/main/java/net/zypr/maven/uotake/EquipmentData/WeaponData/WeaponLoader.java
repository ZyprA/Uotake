package net.zypr.maven.uotake.EquipmentData.WeaponData;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeaponLoader {
    private final Map<String, Weapon> weaponMap = new HashMap<>();


    public void loadWeapons(FileConfiguration config) {

        weaponMap.clear();
        // "weapon" セクションを取得
        ConfigurationSection weaponSection = config.getConfigurationSection("weapon");
        if (weaponSection == null) {
            return;
        }

        for (String key : weaponSection.getKeys(false)) {
            ConfigurationSection weaponData = weaponSection.getConfigurationSection(key);

            if (weaponData != null) {
                String name = weaponData.getString("name");
                String wm_id = weaponData.getString("wm_id");
                int cost = weaponData.getInt("cost", -1);
                int tier = weaponData.getInt("tier", -1);
                WeaponCategory category = null;
                WeaponType type = null;
                try {
                    category = WeaponCategory.valueOf(weaponData.getString("category").toUpperCase());
                } catch (IllegalArgumentException e) {
                    // 不正なcategory値があった場合の処理
                    System.out.println("無効なcategory: " + weaponData.getString("category"));
                }

                try {
                    type = WeaponType.valueOf(weaponData.getString("type").toUpperCase());
                } catch (IllegalArgumentException e) {
                    // 不正なtype値があった場合の処理
                    System.out.println("無効なtype: " + weaponData.getString("type"));
                }

                if (cost != -1 && tier != -1 && category != null && type != null) {
                    Weapon weapon = new Weapon();
                    weapon.setId(key);
                    weapon.setName(name);
                    weapon.setWm_id(wm_id);
                    weapon.setCost(cost);
                    weapon.setTier(tier);
                    weapon.setDescription(weaponData.getStringList("description"));
                    weapon.setCategory(category);
                    weapon.setType(type);
                    weapon.setAmmo(weaponData.getInt("ammo", 60));
                    weapon.setCmd(weaponData.getInt("cmd", 0));
                    weapon.setAmount(weaponData.getInt("amount", 1));
                    weapon.setMaterial(Material.getMaterial(weaponData.getString("item", "AIR")));

                    // 名前をキーにしてマップに保存
                    weaponMap.put(key, weapon);
                } else {
                    System.out.println("§cWeaponLoader: " + key + " is invalid.");
                }
            }
        }
    }

    public Weapon getWeaponByName(String name) {
        return weaponMap.get(name);
    }

    public List<Weapon> getWeaponsByType(WeaponType type) {
        return weaponMap.values().stream()
                .filter(weapon -> weapon.getType().equals(type))
                .collect(Collectors.toList());
    }

}
