package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.zypr.maven.uotake.Uotake.getRoot;

public class gameselect {
    public static Inventory get() {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), 27 , "§8§nゲーム選択メニュー - クリックで選択");
        YamlConfiguration config;
        config = YamlConfiguration.loadConfiguration(new File(getRoot() + "/setting/serverstatus.yml"));
        ItemStack item;
        String display;
        int slot;
        int status;
        Material material;
        List<String> servers = (new ArrayList<>(Arrays.asList("tdm1","mgo1")));
        for (String server : servers) {
            switch(server) {
                case "tdm1":
                    display = "§eチームデスマッチ§7-TDM§b[1]";
                    slot = 0;
                    material = Material.EMERALD;
                    break;
                case "mgo1":
                    display = "§eチームデスマッチ[小]§7-MGO§b[1]";
                    slot = 2;
                    material = Material.FEATHER;
                    break;
                default:
                    display = null;
                    slot = 27;
                    material = Material.AIR;
                    break;
            }
            status = config.getInt(server + ".status");
            item = new ItemStack((status == 1) ? material : Material.STONE_BUTTON, 1);
            NBTAPI.addNBT(item, "action", "server@" + server);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(display);
                meta.setLore(new ArrayList<>((status == 1) ? Arrays.asList("§aオンライン: §2§l"+config.getInt(server + ".players")+"/"+config.getInt(server + ".max_players"),"§b[ プレイ中 ]") : Collections.singletonList("§cオフライン")));
            }
            item.setItemMeta(meta);
            inventory.setItem(slot,item);
        }

        return inventory;
    }
}
