package net.zypr.maven.uotake.events;

import net.zypr.maven.uotake.util.InvHolder;
import net.zypr.maven.uotake.classes.ItemAction;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static net.zypr.maven.uotake.util.CooldownManager.isCooldownActive;
import static net.zypr.maven.uotake.util.CooldownManager.setCooldown;


public class InventoryClick implements Listener {
    @EventHandler
    public void click(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof InvHolder) {
            event.setCancelled(true);
            if (isCooldownActive(p.getUniqueId(),200)) {return;}
            setCooldown(p.getUniqueId());
            if (event.getCurrentItem() == null) {return;}
            String tag = NBTAPI.getNBT(event.getCurrentItem(),"action");
            if (tag == null) {return;}
            ItemAction.action(p,tag);
        }
    }
}
