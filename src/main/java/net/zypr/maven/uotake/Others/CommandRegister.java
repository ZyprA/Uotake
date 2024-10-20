package net.zypr.maven.uotake.Others;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import net.zypr.maven.uotake.Uotake;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CommandRegister {
    public static void load() {
        new CommandAPICommand("setlobby")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {
                    if (Lobby.setLobby(((Player) sender).getLocation())) {
                        sender.sendMessage(ChatColor.GREEN + "成功しました");
                    } else {
                        sender.sendMessage(ChatColor.RED + "失敗しました");
                    }
                })
                .register();
        new CommandAPICommand("reconfig")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {
                    Uotake.config = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/config.yml"));
                    Uotake.menu = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/lobby/menu.yml"));
                    Uotake.inventory = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/lobby/inventory.yml"));
                    sender.sendMessage(ChatColor.GREEN + "読み込ました");
                })
                .register();
    }
}
