package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.EquipmentData.ArmorData.Armor;
import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorType;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
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
        boolean armorboolean = playerData.getArmorBoolean();
        List<Integer> slots = Arrays.asList(0, 1, 3, 5, 6);
        ItemCreator itemCreator = new ItemCreator();

        for (Integer slot : slots) {
            if (slot == 0 || slot == 1) {
                setupWeaponSet(inventory, playerData, itemCreator, slot, selectedSet);
            } else if (slot == 3) {
                setupArmor(inventory, playerData, itemCreator, armorboolean, slot);
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
                .setAction("setting@equip_" + setIdentifier + "/OpenMenu@equip_editor").generate());

        setupWeapon(inventory, playerData, itemCreator, setIdentifier, WeaponCategory.MAIN, slot + 18);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, WeaponCategory.SUB, slot + 27);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, WeaponCategory.GRENADE, slot + 36);
        setupWeapon(inventory, playerData, itemCreator, setIdentifier, WeaponCategory.FOOD, slot + 45);
    }

    private static void setupWeapon(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, String setIdentifier, WeaponCategory category, int slot) {
        Weapon weapon = playerData.getEquipment().get(setIdentifier).get(category);
        inventory.setItem(slot, itemCreator.setMaterial(weapon.getMaterial())
                .setCmd(weapon.getCmd())
                .setName("§7" + getWeaponCategoryName(category) + ": §f" + weapon.getId())
                .setAction("OpenMenu@equip_" + setIdentifier + "_" + category.getName() + "/Sound@entity.bat.takeoff,1,1")
                .setLore(weapon.getDescription())
                .setAmount(weapon.getAmount())
                .generate());
        itemCreator.reset();
    }

    private static String getWeaponCategoryName(WeaponCategory weaponType) {
        return switch (weaponType) {
            case MAIN -> "メイン武器";
            case SUB -> "サブ武器";
            case GRENADE -> "爆弾";
            case FOOD -> "食料";
            default -> "";
        };
    }

    private static void setupArmor(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, boolean armorboolean, int slot) {
        inventory.setItem(slot, itemCreator.setMaterial(Material.BOOK).setName("§f防具").generate());
        inventory.setItem(slot + 9, itemCreator.setMaterial(armorboolean ? Material.LIME_DYE : Material.GRAY_DYE)
                .setName(armorboolean ? "§a防具-ON" : "§b防具§f-§cOFF")
                .setAction(armorboolean ? "setting@armbool_false/OpenMenu@equip_editor" : "setting@armbool_true/OpenMenu@equip_editor").generate());

        setupArmorPiece(inventory, playerData, itemCreator, ArmorType.HEAD, slot + 18);
        setupArmorPiece(inventory, playerData, itemCreator, ArmorType.BODY, slot + 27);
        setupArmorPiece(inventory, playerData, itemCreator, ArmorType.LEGS, slot + 36);
        setupArmorPiece(inventory, playerData, itemCreator, ArmorType.FOOT, slot + 45);
    }

    private static void setupArmorPiece(Inventory inventory, PlayerData playerData, ItemCreator itemCreator, ArmorType armorType, int slot) {
        Armor armor = playerData.getArmor().get(armorType);
        inventory.setItem(slot, itemCreator.setMaterial(armor.getMaterial())
                .setCmd(armor.getCmd())
                .setName("§7" + getArmorTypeName(armorType) + ": §f" + armor.getId())
                .setAction("OpenMenu@equip_armor_" + armorType.name() + "/Sound@entity.bat.takeoff,1,1")
                .setLore(armor.getDescription())
                .generate());
        itemCreator.reset();
    }

    private static String getArmorTypeName(ArmorType armorType) {
        return switch (armorType) {
            case HEAD -> "頭部防具";
            case BODY -> "上半身防具";
            case LEGS -> "下半身防具";
            case FOOT -> "脚防具";
            default -> "";
        };
    }
}
