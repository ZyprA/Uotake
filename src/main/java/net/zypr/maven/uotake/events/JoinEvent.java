package net.zypr.maven.uotake.events;

import fr.mrmicky.fastboard.FastBoard;
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
import org.bukkit.scoreboard.Score;

import java.util.Iterator;

public class JoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Lobby.teleportLobby(player);
        User user = new User(player);
        user.arrangeData();
        Uotake.playerDataManager.loadPlayerData(player);
        InvLoader.load(player.getInventory(), Uotake.inventory, "lobby", player);
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
        Player player = event.getPlayer();
        Uotake.playerDataManager.savePlayerData(player);
        Uotake.playerDataManager.removePlayerData(player.getUniqueId());
        Scoreboard.deleteBoard(player);
    }
}