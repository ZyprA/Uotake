package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.util.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InvLoader {
    public static void load(org.bukkit.inventory.Inventory inventory,List<ItemStack> items) {
        inventory.clear();
        int loop = 0;
        for (ItemStack item : items) {
            inventory.setItem(loop,item);
            loop++;
        }
    }

    public static void lobby(Player p) {
        p.getInventory().clear();
        Inventory inventory = p.getInventory();
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        String select = playerData.getSelect();
        String weapon;
        ItemCreator creator = new ItemCreator();
        weapon = playerData.getEquipment().get(select).get("main");
        inventory.setItem(0,creator.setMaterial(Weapon.getMaterial(weapon,"main")).setName(Weapon.getName(weapon, "main")).setCmd(Weapon.getCmd(weapon,"main")).generate());
        weapon = playerData.getEquipment().get(select).get("sub");
        inventory.setItem(1,creator.setMaterial(Weapon.getMaterial(weapon,"sub")).setName(Weapon.getName(weapon, "sub")).setCmd(Weapon.getCmd(weapon,"sub")).generate());
        weapon = playerData.getEquipment().get(select).get("grenade");
        inventory.setItem(2,creator.setMaterial(Weapon.getMaterial(weapon,"grenade")).setName(Weapon.getName(weapon, "grenade")).setCmd(Weapon.getCmd(weapon,"grenade")).setAmount(Weapon.getAmount(weapon,"grenade")).generate());
        weapon = playerData.getEquipment().get(select).get("food");
        inventory.setItem(3,creator.setMaterial(Weapon.getMaterial(weapon,"food")).setName(Weapon.getName(weapon, "food")).generate());
        creator.reset();
        inventory.setItem(6,creator.setMaterial(Material.CLOCK).setName("§bNetwork Menu§7>>§c§l右クリック").setAction("OpenMenu@mainmenu/Sound@entity.firework_rocket.large_blast,1,2").generate());
        inventory.setItem(7,creator.setMaterial(Material.BOOK).setName("§bプレイヤー情報§7>>§c§l右クリック").setAction("status").generate());
        inventory.setItem(8,creator.setMaterial(Material.BRICK).setName("§b武器選択§7>>§c§l右クリック").setAction("OpenMenu@equipeditor/Sound@block.chest.open,1,1").generate());
        creator.reset();
    }
}
