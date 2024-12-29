package net.zypr.maven.uotake.Menu;

import net.zypr.maven.uotake.Menu.packs.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Menu {

    public static void open(Player p, MenuName type) {
    p.closeInventory();
    switch (type) {
        case GAMES_SELECT:
            p.openInventory(GameSelector.get());
            break;
        case MAIN_MENU:
            p.openInventory(MainMenu.get());
            break;
        case EQUIP_EDITOR:
            p.openInventory(EquipEditor.get(p));
            break;
        case SHOP_AR:
        case SHOP_HG:
        case SHOP_RL:
        case SHOP_SG:
        case SHOP_SMG:
        case SHOP_SR:
            p.openInventory(Shop.get(type, p));
            break;
        default:
            p.sendMessage(ChatColor.RED + "メニュー情報が存在しません．管理者に問い合わせてください");
            break;
    }
}

}
