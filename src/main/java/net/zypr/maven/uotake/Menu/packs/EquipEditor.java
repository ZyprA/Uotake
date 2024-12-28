package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class EquipEditor {
    public static Inventory get(Player player) {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), 54, "§8装備 - 武器をクリックで選択画面表示");
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(player.getUniqueId());
        String selectedSet = playerData.getSelect();
        String armorStatus = playerData.getEquipment().get("armor").get("boolean");
        List<Integer> slots = Arrays.asList(0, 1, 3, 5, 6);
        ItemCreator itemCreator = new ItemCreator();

        for (Integer slot : slots) {
            if (slot == 0 || slot == 1) {
                setupWeaponSet(inventory, playerData, itemCreator, slot, selectedSet);
            } else if (slot == 3) {
                setupArmor(inventory, playerData, itemCreator, armorStatus, slot);
            }
        }
        return inventory;
    }

    private static void setupWeaponSet(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, int slot, String selectedSet) {
        String setIdentifier = (slot == 0) ? "a" : "b";
        String setName = (slot == 0) ? "A" : "B";
        inventory.setItem(slot, itemCreator.setMaterial(Material.BOOK).setName("§f装備セット" + setName).generate());
        inventory.setItem(slot + 9, itemCreator.setMaterial(Objects.equals(selectedSet, setIdentifier) ? Material.LIME_DYE : Material.GRAY_DYE)
                .setName(Objects.equals(selectedSet, setIdentifier) ? "§a武器セット" + setName + "-ON" : "§b武器セット" + setName + "§f-§cOFF")
                .setAction("setting@equip." + setIdentifier + "/OpenMenu@equipeditor").generate());

        setupWeapon(inventory, playerData, itemCreator, setIdentifier, "main", slot + 18);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, "sub", slot + 27);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, "grenade", slot + 36);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, "food", slot + 45);
    }

    private static void setupWeapon(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, String setIdentifier, String weaponType, int slot) {
        String weaponId = playerData.getEquipment().get(setIdentifier).get(weaponType);
        inventory.setItem(slot, itemCreator.setMaterial(Weapon.getMaterial(weaponId, weaponType))
                .setCmd(Weapon.getCmd(weaponId, weaponType))
                .setName("§7" + getWeaponTypeName(weaponType) + ": §f" + weaponId)
                .setAction("setting@equip." + setIdentifier + "." + weaponType + "/Sound@entity.bat.takeoff,1,1")
                .setLore(Weapon.getDescription(weaponId, weaponType))
                .setAmount(Weapon.getAmount(weaponId, weaponType))
                .generate());
        itemCreator.reset();
    }

    private static String getWeaponTypeName(String weaponType) {
        switch (weaponType) {
            case "main":
                return "メイン武器";
            case "sub":
                return "サブ武器";
            case "grenade":
                return "爆弾";
            case "food":
                return "食料";
            default:
                return "";
        }
    }

    private static void setupArmor(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, String armorStatus, int slot) {
        inventory.setItem(slot, itemCreator.setMaterial(Material.BOOK).setName("§f防具").generate());
        inventory.setItem(slot + 9, itemCreator.setMaterial(Objects.equals(armorStatus, "true") ? Material.LIME_DYE : Material.GRAY_DYE)
                .setName(Objects.equals(armorStatus, "true") ? "§a防具-ON" : "§b防具§f-§cOFF")
                .setAction(Objects.equals(armorStatus, "true") ? "setting@armbool.false/OpenMenu@equipeditor" : "setting@armbool.true/OpenMenu@equipeditor").generate());

        setupArmorPiece(inventory, playerData, itemCreator, "head", slot + 18);
        setupArmorPiece(inventory, playerData, itemCreator, "body", slot + 27);
        setupArmorPiece(inventory, playerData, itemCreator, "legs", slot + 36);
        setupArmorPiece(inventory, playerData, itemCreator, "foot", slot + 45);
    }

    private static void setupArmorPiece(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, String armorType, int slot) {
        String armorId = playerData.getEquipment().get("armor").get(armorType);
        inventory.setItem(slot, itemCreator.setMaterial(Weapon.getMaterial(armorId, armorType))
                .setCmd(Weapon.getCmd(armorId, armorType))
                .setName("§7" + getArmorTypeName(armorType) + ": §f" + armorId)
                .setAction("setting@equip.armor." + armorType + "/Sound@entity.bat.takeoff,1,1")
                .setLore(Weapon.getDescription(armorId, armorType))
                .setAmount(Weapon.getAmount(armorId, armorType))
                .generate());
        itemCreator.reset();
    }

    private static String getArmorTypeName(String armorType) {
        switch (armorType) {
            case "head":
                return "頭部防具";
            case "body":
                return "上半身防具";
            case "legs":
                return "下半身防具";
            case "foot":
                return "脚防具";
            default:
                return "";
        }
    }
}
