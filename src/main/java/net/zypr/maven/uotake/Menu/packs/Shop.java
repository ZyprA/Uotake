package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.classes.InvLoader;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shop {
    public static Inventory get(String param, Player p) {
        String[] params = param.split("\\.");

        Inventory inventory = null;

        if (params.length != 3) {
            inventory = Bukkit.createInventory(new InvHolder(), 9, "§8不明なメニュー: 管理者に問い合わせてください");
            return inventory;
        }

        String title = String.valueOf(Uotake.config.get("display.shop." + params[2]));
        List<String> weapons = Uotake.weaponbytype.getWeapons(params[2]);
        List<ItemStack> itemStacks = new ArrayList<>();
        String category = params[1];
        ItemCreator creator = new ItemCreator();

        weapons.sort(Comparator.comparingInt(weapon -> Weapon.getTier(weapon, category)));

        for (String weapon : weapons) {
            List<String> description = new ArrayList<>();
            if (Weapon.ifPlayerHasWeapon(p, weapon)) {
                description.add("§a<購入済み>");
            } else if (!Weapon.ifPlayerIsAbleToBuyWeaponByTier(p, weapon)) {
                description.add("§c<要Tier解放>");
            }
            int Tier = Weapon.getTier(weapon, category);

            description.add("§3§lTier" + Tier + " / $" + Weapon.getCost(weapon));
            description.addAll(Weapon.getDescription(weapon, category));

            itemStacks.add(creator.setMaterial(Weapon.getMaterial(weapon, category)).setCmd(Weapon.getCmd(weapon, category)).setName("§a§l" + weapon + "§7 - " + "§6Tier" + Tier).setAction("buyweapon@" + weapon).setLore(description).generate());
        }

        inventory = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8§l" + title);
        InvLoader.load(inventory, itemStacks);

        return inventory;
    }
}
