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
        if (params.length == 2 && Objects.equals(params[0], "a") || Objects.equals(params[0], "b")) {
            List<String> weapons;
            if (Weapon.isCategory(params[1])) {
                weapons = playerData.getWeapons(params[1]);
                inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8装備の変更");
                ItemCreator creator = new ItemCreator();
                for (String weapon : weapons) {
                    if (Weapon.ifExists(weapon)) {
                        inventory.setItem(slot, creator.setMaterial(Weapon.getMaterial(weapon, params[1])).setCmd(Weapon.getCmd(weapon, params[1])).setName(weapon).setAction("setting@select." + params[0] + "." + params[1] + "." + weapon).setAmount(Weapon.getAmount(weapon, params[1])).setLore(Weapon.getDescription(weapon, params[1])).generate());
                        slot++;
                    }
                }
            }
        } else if (params.length == 2 && Objects.equals(params[0], "armor") && (Objects.equals(params[1], "head") || Objects.equals(params[1], "body") || Objects.equals(params[1], "legs") || Objects.equals(params[1], "foot"))) {
            List<String> weapons;
            if (Weapon.isCategory(params[1])) {
                weapons = playerData.getWeapons(params[1]);
                inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8装備の変更");
                ItemCreator creator = new ItemCreator();
                for (String weapon : weapons) {
                    if (Weapon.ifExists(weapon)) {
                        inventory.setItem(slot, creator.setMaterial(Weapon.getMaterial(weapon, params[1])).setCmd(Weapon.getCmd(weapon, params[1])).setName(weapon).setAction("setting@select." + params[0] + "." + params[1] + "." + weapon).setAmount(Weapon.getAmount(weapon, params[1])).setLore(Weapon.getDescription(weapon, params[1])).generate());
                        slot++;
                    }
                }
            }
        } else {
            inventory = Bukkit.createInventory(new InvHolder(), 9, "§8不明なメニュー: 管理者に問い合わせてください");
        }
        return inventory;
    }
}
