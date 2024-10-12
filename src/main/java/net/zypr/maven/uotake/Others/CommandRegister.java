package net.zypr.maven.uotake.Others;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.*;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.classes.ItemAction;
import net.zypr.maven.uotake.classes.Lobby;
import net.zypr.maven.uotake.classes.Menu;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.util.ConfigUtil;
import net.zypr.maven.uotake.util.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Objects;

public class CommandRegister {
    public static void load() {
        new CommandAPICommand("broadcastmsg")
                .withArguments(new GreedyStringArgument("message")) // The arguments
                .withAliases("broadcast", "broadcastmessage")       // Command aliases
                .withPermission(CommandPermission.OP)               // Required permissions
                .executes((sender, args) -> {
                    String message =  String.valueOf(args.get("message"));
                    Bukkit.getServer().broadcastMessage(message);
                })
                .register();
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
        new CommandAPICommand("getnbt")
                .withPermission(CommandPermission.OP)
                .withArguments(new StringArgument("tag"))
                .executes((sender, args) -> {
                    sender.sendMessage(Objects.requireNonNull(NBTAPI.getNBT(((Player) sender).getInventory().getItemInMainHand(), (String) args.get("tag"))));
                })
                .register();
        new CommandAPICommand("test")
                .withPermission(CommandPermission.OP)
                .withArguments(new StringArgument("id"))
                .executes((sender, args) -> {
                    int i = Weapon.buyWeapon((Player) sender,(String) args.get("id"));
                    if (i == 0) {
                        sender.sendMessage(ChatColor.GREEN + "購入しました");
                    } else if (i == 1) {
                        sender.sendMessage(ChatColor.RED + "所持金語りません");
                    } else if (i == 2) {
                        sender.sendMessage(ChatColor.RED + "すでに所持しています");
                    } else if (i == 3) {
                        sender.sendMessage(ChatColor.RED + "武器データが存在しません");
                    } else if (i == 4) {
                        sender.sendMessage(ChatColor.DARK_RED + "エラーが発生しました");
                    }
                })
                .register();
        new CommandAPICommand("menu")
                .withPermission(CommandPermission.OP)
                .withArguments(new StringArgument("id"))
                .executes((sender, args) -> {
                    Menu.open((Player) sender, String.valueOf(args.get("id")));
                })
                .register();
        new CommandAPICommand("action")
                .withPermission(CommandPermission.OP)
                .withArguments(new GreedyStringArgument("action"))
                .executes((sender, args) -> {
                    ItemAction.action((Player) sender,String.valueOf(args.get("action")));
                })
                .register();
        new CommandAPICommand("im")
                .withPermission(CommandPermission.OP)
                .withArguments(new GreedyStringArgument("material"))
                .executes((sender, args) -> {
                    ItemStack im = new ItemStack(Material.valueOf(String.valueOf(args.get("material"))));
                    ((Player) sender).getInventory().setItem(0, im);
                })
                .register();
        new CommandAPICommand("sendserver")
                .withPermission(CommandPermission.OP)
                .withArguments(new PlayerArgument("player"), new StringArgument("server"))
                .executes((sender, args) -> {
                    sender.sendMessage(ChatColor.GRAY + "Sending " + args.get("player") + " to " + args.get("server") + "...");
                    Proxy.sendPlayerToServer((Player) args.get("player"), (String) args.get("server"));
                })
                .register();
        new CommandAPICommand("reconfig")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {
                    Uotake.config = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/config.yml"));
                    Uotake.menu = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/menu.yml"));
                    Uotake.inventory = YamlConfiguration.loadConfiguration(new File(Uotake.getRoot() + "/setting/inventory.yml"));
                    Uotake.variable = new ConfigUtil("/setting/variable.yml");
                    sender.sendMessage(ChatColor.GREEN + "読み込ました");
                })
                .register();
    }
}
