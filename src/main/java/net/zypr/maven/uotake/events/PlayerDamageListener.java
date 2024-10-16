package net.zypr.maven.uotake.events;

import net.zypr.maven.uotake.Others.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {
    @EventHandler
    public void OnPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Lobby.teleportLobby(((Player) e.getEntity()));
            }
        }
    }
}
