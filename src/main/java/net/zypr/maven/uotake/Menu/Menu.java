package net.zypr.maven.uotake.Menu;

import net.zypr.maven.uotake.Menu.packs.equip;
import net.zypr.maven.uotake.Menu.packs.equipeditor;
import net.zypr.maven.uotake.Menu.packs.gameselect;
import net.zypr.maven.uotake.Menu.packs.mainmenu;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.classes.InvLoader;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Menu {
    public static void open(Player p, String id) {
        p.closeInventory();
        if (Objects.equals(id, "gameselect")) {
            p.openInventory(gameselect.get());
        } else if (Objects.equals(id, "mainmenu")) {
            p.openInventory(mainmenu.get());
        } else if (Objects.equals(id, "equipeditor")) {
            p.openInventory(equipeditor.get(p));
        } else if (id.startsWith("equip.")) {
            String[] params = id.split("\\.");
            p.openInventory(equip.get(params[1] + "." + params[2], p));
        } else if (id.startsWith("shop.")) {
            String[] params = id.split("\\.");
            if (params.length != 2) {
                return;
            }
            String title = String.valueOf(Uotake.config.get("display.shop." + params[1]));
            List<String> weapons = Uotake.weaponbytype.getWeapons(params[1]);
            List<ItemStack> itemStacks = new ArrayList<>();
            String category = "null";
            if (Objects.equals(params[1], "ar") || Objects.equals(params[1], "sg") || Objects.equals(params[1], "sr") || Objects.equals(params[1], "smg") || Objects.equals(params[1], "rl")) {
                category = "main";
            } else if (Objects.equals(params[1], "hg")) {
                category = "sub";
            }
            for (String weapon : weapons) {
                ItemStack im = new ItemStack(Weapon.getMaterial(weapon, category));
                NBTAPI.addNBT(im, "action", "buyweapon@" + weapon);
                ItemMeta itemMeta = im.getItemMeta();
                if (itemMeta != null) {
                    itemMeta.setCustomModelData(Weapon.getCmd(weapon, category));
                    itemMeta.setDisplayName(Weapon.getName(weapon, category));
                }
                im.setItemMeta(itemMeta);
                itemStacks.add(im);
            }
            Inventory inv = Bukkit.createInventory(new InvHolder(), (((weapons.size() - 1) / 9) + 1) * 9, "§8§l" + title);
            InvLoader.load(inv, itemStacks);
            p.openInventory(inv);
        } else {
            p.sendMessage(ChatColor.RED + "メニュー情報が存在しません．管理者に問い合わせてください");
        }
    }

}
