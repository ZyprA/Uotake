package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.util.NBTAPI;
import net.zypr.maven.uotake.util.PlaceHolder;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class InvLoader {
    public static void load(org.bukkit.inventory.Inventory inventory, FileConfiguration yaml, String path, Player p) {
        List<Map<?, ?>> itemList = yaml.getMapList(path);
        inventory.clear();

        for (Map<?, ?> itemData : itemList) {
            // 必要なデータが揃っていなければスキップ
            if (!itemData.containsKey("type")) continue;

            // アイテムID（デフォルト値を設定）
            byte itemId = itemData.containsKey("iv") ? Byte.parseByte(PlaceHolder.r(itemData.get("iv").toString(),p,"iv")) : 0;
            String type = PlaceHolder.r(itemData.get("type").toString(), p, "item");
            ItemStack item = new ItemStack(Material.valueOf(type), 1, itemId);

            // NBTタグの追加
            if (itemData.containsKey("tags")) {
                ((Map<String, Object>) itemData.get("tags")).forEach((tag, value) -> {
                    NBTAPI.addNBT(item, tag, value.toString());
                });
            }

            // アイテムメタデータの設定
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta != null) {
                if (itemData.containsKey("name")) {
                    itemMeta.setDisplayName(PlaceHolder.r(itemData.get("name").toString(), p, "name"));
                }
                if (itemData.containsKey("cmd")) {
                    itemMeta.setCustomModelData(Integer.valueOf(PlaceHolder.r(itemData.get("cmd").toString(), p, "cmd")));
                }
                item.setItemMeta(itemMeta);
            }

            // スロット設定
            int slot = itemData.containsKey("slot") ? (int) itemData.get("slot") : 0;
            inventory.setItem(slot, item);
        }
    }
}
