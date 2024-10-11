package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.util.InvHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class Menu {
    public static void open(Player p, String id) {
        p.closeInventory();
        if (Objects.equals(id, "shop.ar")) {
 //
        } else if (Uotake.menu.contains(id)) { //menu.ymlから読み込み
            Inventory inv = Bukkit.createInventory(new InvHolder(), (Integer) Uotake.menu.get(id + ".size"),(String) Objects.requireNonNull(Uotake.menu.get(id + ".title")));
            InvLoader.load(inv,Uotake.menu,id + ".contents", p);
            p.openInventory(inv);
        } else {
            p.sendMessage(ChatColor.RED + "メニュー情報が存在しません．管理者に問い合わせてください");
        }
    }

}
