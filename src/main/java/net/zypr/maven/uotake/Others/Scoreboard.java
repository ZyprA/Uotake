package net.zypr.maven.uotake.Others;
import fr.mrmicky.fastboard.FastBoard;
import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard implements Listener {
    private static FastBoard board;
    private static final Map<Player, FastBoard> boards = new HashMap<>();
    private static final int LOOP_TIME = 100; //5秒

    public static void deleteBoard(Player player) {
        boards.remove(player);
    }
    public void showScoreboard() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
                    if (boards.get(p) == null) {
                        board = new FastBoard(p);
                        p.sendMessage("ボードを作成しました");
                        boards.put(p, board);
                    } else {
                        board = boards.get(p);
                    }

                    board.updateTitle("§b§lUotakeGunOnline");
                    board.updateLines(
                            "§f================",
                            "§d§l Your Status",
                            "§a§lKill",
                            "§f" + playerData.getBattleStatus().getKills(),
                            "§f",
                            "§c§lDeath",
                            "§f" + playerData.getBattleStatus().getDeaths(),
                            "§f",
                            "§e§lMoney",
                            "§f" + playerData.getMoney(),
                            "§f",
                            "§6§lRank",
                            "§f" + playerData.getRank(),
                            "================"
                    );
                }
            }
        }.runTaskTimer(Uotake.getPlugin(), 0L, LOOP_TIME);
    }
}
