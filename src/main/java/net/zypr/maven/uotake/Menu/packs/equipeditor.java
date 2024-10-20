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

import java.util.*;


public class equipeditor {
    public static Inventory get(Player p) {
        Inventory inventory = Bukkit.createInventory(new InvHolder(), 54 , "§8装備 - 武器をクリックで選択画面表示");
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        String select = playerData.getSelect();
        List<Integer> slots = (new ArrayList<>(Arrays.asList(0,1,3,5,6)));
        ItemCreator creator = new ItemCreator();
        for (Integer slot : slots) {
            if (slot == 0 || slot == 1) { //装備セットA,B
                String aorb = (slot == 0) ? "a" : "b";
                String AorB = (slot == 0) ? "A" : "B";
                inventory.setItem(slot,creator.setMaterial(Material.BOOK).setName(slot == 0 ? "§f装備セットA" : "§f装備セットB").generate());
                inventory.setItem(slot + 9,creator.setMaterial(Objects.equals(select, aorb) ? Material.LIME_DYE : Material.GRAY_DYE).setName(Objects.equals(select, aorb) ? "§a武器セット" + AorB + "-ON" : "§b武器セット"+ AorB +"§f-§cOFF").setAction("setting@equip." + aorb + "/OpenMenu@equipeditor").generate());
                String weapon =playerData.getEquipment().get(aorb).get("main");
                inventory.setItem(slot + 18, creator.setMaterial(Weapon.getMaterial(weapon,"main")).setCmd(Weapon.getCmd(weapon,"main")).setName("§7メイン武器: §f" + weapon).setAction("setting@equip."+aorb+".main/Sound@entity.bat.takeoff,1,1").setLore(Weapon.getDescription(weapon,"main")).generate());
                weapon = playerData.getEquipment().get(aorb).get("sub");
                inventory.setItem(slot + 27, creator.setMaterial(Weapon.getMaterial(weapon,"sub")).setCmd(Weapon.getCmd(weapon,"sub")).setName("§7サブ武器: §f" + weapon).setAction("setting@equip."+aorb+".sub/Sound@entity.bat.takeoff,1,1").setLore(Weapon.getDescription(weapon,"sub")).generate());
                weapon = playerData.getEquipment().get(aorb).get("grenade");
                inventory.setItem(slot + 36, creator.setMaterial(Weapon.getMaterial(weapon,"grenade")).setCmd(Weapon.getCmd(weapon,"grenade")).setName("§7爆弾: §f" + weapon).setAction("setting@equip."+aorb+".grenade/Sound@entity.bat.takeoff,1,1").setAmount(Weapon.getAmount(weapon,"grenade")).setLore(Weapon.getDescription(weapon,"grenade")).generate());
                weapon = playerData.getEquipment().get(aorb).get("food");
                inventory.setItem(slot + 45, creator.setMaterial(Weapon.getMaterial(weapon,"food")).setCmd(Weapon.getCmd(weapon,"food")).setName("§7食料: §f" + weapon).setAction("setting@equip."+aorb+".food/Sound@entity.bat.takeoff,1,1").setLore(Weapon.getDescription(weapon,"food")).generate());
                creator.reset();
            }
        }
        return inventory;
    }
}
