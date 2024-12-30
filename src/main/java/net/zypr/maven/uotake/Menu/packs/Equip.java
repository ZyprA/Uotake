package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.EquipmentData.ArmorData.Armor;
import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorType;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Objects;

public class Equip {
    public static Inventory get(String param, Player p) { //a.main
    String[] params = param.split("\\.");
    PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
    Inventory inventory = null;
        if (params.length == 3) {
            if (Objects.equals(params[1], "a") || Objects.equals(params[1], "b")) {
                WeaponCategory category = WeaponCategory.valueOf(params[2].toUpperCase());
                inventory = createWeaponInventory(playerData, category, params[1]);
            } else if (Objects.equals(params[1], "armor")) {
                ArmorType type = ArmorType.valueOf(params[2].toUpperCase());
                inventory = createArmorInventory(playerData, type, params[1]);
        }
    }

    if (inventory == null) {
        inventory = Bukkit.createInventory(new InvHolder(), 9, "§8不明なメニュー: 管理者に問い合わせてください");
    }

    return inventory;
}

private static Inventory createWeaponInventory(PlayerData playerData, WeaponCategory category, String param) {
    List<Weapon> weapons = playerData.getWeapons(category);
    Inventory inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8装備の変更");
    ItemCreator creator = new ItemCreator();
    int slot = 0;

    for (Weapon weapon : weapons) {
        inventory.setItem(slot, creator.setMaterial(weapon.getMaterial())
                .setCmd(weapon.getCmd())
                .setName(weapon.getName())
                .setAction("setting@select_" + param + "_" + category + "_" + weapon.getId())
                .setAmount(weapon.getAmount())
                .setLore(weapon.getDescription())
                .generate());
        slot++;
    }

    return inventory;
}

    private static Inventory createArmorInventory(PlayerData playerData, ArmorType armorType, String param) {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), 9, "§8装備の変更");
        ItemCreator creator = new ItemCreator();
        int slot = 0;
        List<Armor> armors = null;
        switch (armorType) {
            case HEAD:
                armors = playerData.getHead();
                break;
            case BODY:
                armors = playerData.getBody();
                break;
            case LEGS:
                armors = playerData.getLegs();
                break;
            case FOOT:
                armors = playerData.getFoot();
                break;
            default:
                break;
        }
        for (Armor armor : armors) {
            inventory.setItem(slot, creator.setMaterial(armor.getMaterial())
                    .setCmd(armor.getCmd())
                    .setName(armor.getName())
                    .setAction("setting@select_" + param + "_" + armorType.name() +  "_" + armor.getId())
                    .setLore(armor.getDescription())
                    .generate());
            slot++;
        }

        return inventory;
    }
}
