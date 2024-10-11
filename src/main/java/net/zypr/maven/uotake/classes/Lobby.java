package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Lobby {


    public static boolean setLobby(Location location) {
        Uotake.variable.getConfig().set("lobby.location", location);
        return Uotake.variable.save();
    }

    public static void teleportLobby(Player p) {
        p.teleport((Location) Objects.requireNonNull(Uotake.variable.getConfig().get("lobby.location")));
    }

}
