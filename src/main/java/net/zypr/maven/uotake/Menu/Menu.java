package net.zypr.maven.uotake.Menu;

import net.zypr.maven.uotake.Menu.packs.Equip;
import net.zypr.maven.uotake.Menu.packs.EquipEditor;
import net.zypr.maven.uotake.Menu.packs.GameSelector;
import net.zypr.maven.uotake.Menu.packs.MainMenu;
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

public class Menu {
    public static void open(Player p, String id) {
    p.closeInventory();
    switch (id) {
        case "gameselect":
            p.openInventory(GameSelector.get());
            break;
        case "mainmenu":
            p.openInventory(MainMenu.get());
            break;
        case "equipeditor":
            p.openInventory(EquipEditor.get(p));
            break;
        default:
            if (id.startsWith("equip.")) {
                handleEquipMenu(p, id);
            } else if (id.startsWith("shop.")) {
                handleShopMenu(p, id);
            } else {
                p.sendMessage(ChatColor.RED + "メニュー情報が存在しません．管理者に問い合わせてください");
            }
            break;
    }
}

private static void handleEquipMenu(Player p, String id) {
    String[] params = id.split("\\.");
    p.openInventory(Equip.get(params[1] + "." + params[2], p));
}

private static void handleShopMenu(Player p, String id) {
    String[] params = id.split("\\.");
    if (params.length != 2) {
        return;
    }
    String title = String.valueOf(Uotake.config.get("display.shop." + params[1]));
    List<String> weapons = Uotake.weaponbytype.getWeapons(params[1]);
    List<ItemStack> itemStacks = new ArrayList<>();
    String category = getCategory(params[1]);

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
}

private static String getCategory(String type) {
    switch (type) {
        case "ar":
        case "sg":
        case "sr":
        case "smg":
        case "rl":
            return "main";
        case "hg":
            return "sub";
        default:
            return "null";
    }
}

}
