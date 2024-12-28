package net.zypr.maven.uotake.Menu;

import net.zypr.maven.uotake.Menu.packs.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
                p.openInventory(Shop.get(id, p));
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

}
