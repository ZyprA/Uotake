package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
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
    int slot = 0;
    Inventory inventory = null;

    if (params.length == 2) {
        WeaponCategory category = WeaponCategory.valueOf(params[1].toUpperCase());
        if (Objects.equals(params[0], "a") || Objects.equals(params[0], "b")) {
            inventory = createWeaponInventory(playerData, category, params[0]);
        } else if (Objects.equals(params[0], "armor")) {
            inventory = createWeaponInventory(playerData, category, params[0]);
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
                .setAction("setting@select." + param + "." + category + "." + weapon)
                .setAmount(weapon.getAmount())
                .setLore(weapon.getDescription())
                .generate());
        slot++;
    }

    return inventory;
}
}
