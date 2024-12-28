package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
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
        String category = params[1];
        if (Objects.equals(params[0], "a") || Objects.equals(params[0], "b")) {
            inventory = createWeaponInventory(playerData, category, params[0]);
        } else if (Objects.equals(params[0], "armor") && isArmorCategory(category)) {
            inventory = createWeaponInventory(playerData, category, params[0]);
        }
    }

    if (inventory == null) {
        inventory = Bukkit.createInventory(new InvHolder(), 9, "§8不明なメニュー: 管理者に問い合わせてください");
    }

    return inventory;
}

private static Inventory createWeaponInventory(PlayerData playerData, String category, String param) {
    if (!Weapon.isCategory(category)) {
        return null;
    }

    List<String> weapons = playerData.getWeapons(category);
    Inventory inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8装備の変更");
    ItemCreator creator = new ItemCreator();
    int slot = 0;

    for (String weapon : weapons) {
        if (Weapon.ifExists(weapon)) {
            inventory.setItem(slot, creator.setMaterial(Weapon.getMaterial(weapon, category))
                    .setCmd(Weapon.getCmd(weapon, category))
                    .setName(weapon)
                    .setAction("setting@select." + param + "." + category + "." + weapon)
                    .setAmount(Weapon.getAmount(weapon, category))
                    .setLore(Weapon.getDescription(weapon, category))
                    .generate());
            slot++;
        }
    }

    return inventory;
}

private static boolean isArmorCategory(String category) {
    return Objects.equals(category, "head") || Objects.equals(category, "body") || Objects.equals(category, "legs") || Objects.equals(category, "foot");
}
}
