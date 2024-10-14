package net.zypr.maven.uotake.events;

import net.zypr.maven.uotake.Others.Scoreboard;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.classes.InvLoader;
import net.zypr.maven.uotake.Others.Lobby;
import net.zypr.maven.uotake.classes.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Lobby.teleportLobby(event.getPlayer());
        User user = new User(event.getPlayer());
        user.arrangeData();
        Uotake.playerDataManager.loadPlayerData(event.getPlayer());
        InvLoader.load(event.getPlayer().getInventory(), Uotake.inventory, "lobby", event.getPlayer());
        if (Uotake.config.contains("display.login")) {
            Iterator it = Uotake.config.getList("display.login").iterator();
            while(it.hasNext()) {
                event.getPlayer().sendMessage((String) it.next());
                it.remove();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Uotake.playerDataManager.savePlayerData(player);
        Uotake.playerDataManager.removePlayerData(player.getUniqueId());
        Scoreboard.deleteBoard(event.getPlayer());
    }
}