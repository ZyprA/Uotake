package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class equip {
    public static Inventory get(String param, Player p) { //a.main
        ItemStack item;
        String[] params = param.split("\\.");
        int slot = 0;
        Inventory inventory = null;
        if (params.length == 2 && Objects.equals(params[0], "a") || Objects.equals(params[0], "b")) {
            PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
            List<String> weapons = new ArrayList<>();
            switch (params[1]) {
                case "main":
                    weapons = playerData.getMainWeapons();
                    break;
                case "sub":
                    weapons = playerData.getSubWeapons();
                    break;
                case "grenade":
                    weapons = playerData.getGrenades();
                    break;
                case "food":
                    weapons = playerData.getFoods();
                    break;
            }
            inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9 , "§8装備の変更");
            for(String weapon : weapons) {
                if (Weapon.ifExists(weapon)) {
                    item = new ItemStack(Weapon.getMaterial(weapon, params[1]));
                    NBTAPI.addNBT(item, "action", "setting@select." + params[0] + "." + params[1] + "." + weapon); //select.a.main.ak-47
                    ItemMeta itemMeta = item.getItemMeta();
                    if (itemMeta != null) {
                        itemMeta.setCustomModelData(Weapon.getCmd(weapon, params[1]));
                        itemMeta.setDisplayName(Weapon.getName(weapon, params[1]));
                    }
                    item.setItemMeta(itemMeta);
                    inventory.setItem(slot,item);
                    slot++;
                }
            }
        }
        return inventory;
    }
}
