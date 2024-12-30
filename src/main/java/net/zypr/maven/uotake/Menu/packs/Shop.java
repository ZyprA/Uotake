package net.zypr.maven.uotake.Menu.packs;

import net.zypr.maven.uotake.Menu.MenuName;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponType;
import net.zypr.maven.uotake.classes.InvLoader;
import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shop {
    public static Inventory get(MenuName type, Player p) {

        String title = getTitle(type.getName());
        List<Weapon> weapons = getWeapons(WeaponType.valueOf(type.getName().toUpperCase()));

        weapons.sort(Comparator.comparing(Weapon::getTier));
        List<ItemStack> itemStacks = createItemStacks(weapons, p);

        return createInventory(title, itemStacks);
    }

    private static String getTitle(String param) {
        return String.valueOf(Uotake.config.get("display.shop." + param));
    }

    private static List<Weapon> getWeapons(WeaponType type) {
        return Uotake.weaponLoader.getWeaponsByType(type);
    }

    private static List<ItemStack> createItemStacks(List<Weapon> weapons, Player p) {
        List<ItemStack> itemStacks = new ArrayList<>();
        ItemCreator creator = new ItemCreator();



        for (Weapon weapon : weapons) {
            List<String> description = createDescription(weapon, p);
            itemStacks.add(createItemStack(creator, weapon, description));
        }

        return itemStacks;
    }

    private static List<String> createDescription(Weapon weapon, Player p) {
        List<String> description = new ArrayList<>();
        if (weapon.ifPlayerHas(p)) {
            description.add("§a<購入済み>");
        } else if (!weapon.ifPlayerIsAbleToBuyByTier(p)) {
            description.add("§c<要Tier解放>");
        }
        description.add("§3§lTier" + weapon.getTier() + " / $" + weapon.getCost());
        description.addAll(weapon.getDescription());
        return description;
    }

    private static ItemStack createItemStack(ItemCreator creator, Weapon weapon, List<String> description) {
        return creator.setMaterial(weapon.getMaterial())
                .setCmd(weapon.getCmd())
                .setName("§a§l" + weapon.getId() + "§7 - " + "§6Tier" + weapon.getTier())
                .setAction("buyweapon@" + weapon.getId() + "/OpenMenu@shop_" + weapon.getType().getName())
                .setLore(description)
                .generate();
    }

    private static Inventory createInventory(String title, List<ItemStack> itemStacks) {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), (((itemStacks.size() - 1) / 9) + 1) * 9, "§8§l" + title);
        InvLoader.load(inventory, itemStacks);
        return inventory;
    }
}
