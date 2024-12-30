package net.zypr.maven.uotake.Others;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class Proxy {
    public static void sendPlayerToServer(Player player, String server) {
        try {
            Bukkit.broadcastMessage("§aMigration §e" + server + "§7> §b" + player.getName());
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(Uotake.getPlugin(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Error when trying to connect to " + server);
        }
    }


}
