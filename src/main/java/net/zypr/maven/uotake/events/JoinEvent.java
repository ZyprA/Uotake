package net.zypr.maven.uotake.events;

import net.zypr.maven.uotake.Others.Scoreboard;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.classes.InvLoader;
import net.zypr.maven.uotake.Others.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Iterator;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("§ejoin §7> §b" + event.getPlayer().getName());
        Player player = event.getPlayer();
        Lobby.teleportLobby(player);
        Uotake.playerDataManager.loadPlayerData(player);
        InvLoader.lobby(player);
        if (Uotake.config.contains("display.login")) {
            Iterator it = Uotake.config.getList("display.login").iterator();
            while(it.hasNext()) {
                player.sendMessage((String) it.next());
                it.remove();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        Uotake.playerDataManager.savePlayerData(player);
        Uotake.playerDataManager.removePlayerData(player.getUniqueId());
        Scoreboard.deleteBoard(player);
    }
}