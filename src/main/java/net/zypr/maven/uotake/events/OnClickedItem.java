package net.zypr.maven.uotake.events;

import net.zypr.maven.uotake.classes.ItemAction;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static net.zypr.maven.uotake.util.CooldownManager.isCooldownActive;
import static net.zypr.maven.uotake.util.CooldownManager.setCooldown;

public class OnClickedItem implements Listener {
    @EventHandler
    public void menu(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) ||  event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (isCooldownActive(player.getUniqueId(),500)) {return;}
            setCooldown(player.getUniqueId());
            if (NBTAPI.hasNBT(player.getInventory().getItemInMainHand(),"action")) {
                ItemAction.action(player,NBTAPI.getNBT(player.getInventory().getItemInMainHand(),"action"));
            }
        }
    }


}
