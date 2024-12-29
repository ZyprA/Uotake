package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InvLoader {

    public static void load(Inventory inventory, List<ItemStack> items) {
        inventory.clear();
        for (int i = 0; i < items.size(); i++) {
            inventory.setItem(i, items.get(i));
        }
    }

    public static void lobby(Player player) {
        player.getInventory().clear();
        Inventory inventory = player.getInventory();
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(player.getUniqueId());
        String selectedEquipment = playerData.getSelect();
        ItemCreator creator = new ItemCreator();

        setInventoryItem(inventory, 0, creator, playerData, selectedEquipment, WeaponCategory.MAIN);
        setInventoryItem(inventory, 1, creator, playerData, selectedEquipment, WeaponCategory.SUB);
        setInventoryItem(inventory, 2, creator, playerData, selectedEquipment, WeaponCategory.GRENADE);
        setInventoryItem(inventory, 3, creator, playerData, selectedEquipment, WeaponCategory.FOOD);

        creator.reset();
        inventory.setItem(6, creator.setMaterial(Material.CLOCK)
                .setName("§bNetwork Menu§7>>§c§l右クリック")
                .setAction("OpenMenu@main_menu/Sound@entity.firework_rocket.large_blast,1,2")
                .generate());
        inventory.setItem(7, creator.setMaterial(Material.BOOK)
                .setName("§bプレイヤー情報§7>>§c§l右クリック")
                .setAction("status")
                .generate());
        inventory.setItem(8, creator.setMaterial(Material.BRICK)
                .setName("§b武器選択§7>>§c§l右クリック")
                .setAction("OpenMenu@equip_editor/Sound@block.chest.open,1,1")
                .generate());
        creator.reset();
    }

    private static void setInventoryItem(Inventory inventory, int slot, ItemCreator creator, PlayerData playerData, String selectedEquipment, WeaponCategory category) {
        Weapon weapon = playerData.getEquipment().get(selectedEquipment).get(category);
        inventory.setItem(slot, creator
                .setMaterial(weapon.getMaterial())
                .setName(weapon.getName())
                .setCmd(weapon.getCmd())
                .setAmount(weapon.getAmount())
                .generate());

    }

}
