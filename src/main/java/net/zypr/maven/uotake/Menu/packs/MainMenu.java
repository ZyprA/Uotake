package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainMenu {
    public static Inventory get() {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), 27, "§8§nNetwork メニュー");
        ItemStack item;
        String display;
        Material material;
        String action;
        List<Integer> slots = (new ArrayList<>(Arrays.asList(2, 4, 6, 12, 14, 17, 26)));
        for (Integer slot : slots) {
            switch (slot) {
                case 2:
                    material = Material.BRICK;
                    display = "§b武器選択§7>>メニューを開く";
                    action = "OpenMenu@equipeditor/Sound@block.chest.open,1,1";
                    break;
                case 4:
                    material = Material.WRITABLE_BOOK;
                    display = "§bプレイヤーメニュー§7>>メニューを開く";
                    action = "Sound@block.note_block.hat,1,1";
                    break;
                case 6:
                    material = Material.COMPARATOR;
                    display = "§b設定§7>>メニューを開く";
                    action = "OpenMenu@usersetting/Sound@block.note_block.hat,1,1";
                    break;
                case 12:
                    material = Material.COMPASS;
                    display = "§eゲーム選択メニュー§7>>メニューを開く";
                    action = "OpenMenu@gameselect/Sound@block.note_block.hat,1,1";
                    break;
                case 14:
                    material = Material.CLOCK;
                    display = "§eロビーメニュー§7>>メニューを開く";
                    action = "Sound@block.note_block.hat,1,1";
                    break;
                case 17:
                    material = Material.EXPERIENCE_BOTTLE;
                    display = "§bVIPメニュー§7>>メニューを開く";
                    action = "Sound@block.note_block.hat,1,1";
                    break;
                case 26:
                    material = Material.ENCHANTED_BOOK;
                    display = "§c管理者メニュー§7>>メニューを開く";
                    action = "Sound@block.note_block.hat,1,1";
                    break;
                default:
                    display = null;
                    material = Material.AIR;
                    action = null;
                    break;
            }
            item = new ItemStack(material, 1);
            NBTAPI.addNBT(item, "action", action);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(display);
            }
            item.setItemMeta(meta);
            inventory.setItem(slot, item);
        }

        return inventory;
    }
}
