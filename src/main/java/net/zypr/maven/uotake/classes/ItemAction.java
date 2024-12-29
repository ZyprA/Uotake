package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Menu.Menu;
import net.zypr.maven.uotake.Menu.MenuName;
import net.zypr.maven.uotake.Others.Proxy;
import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Objects;

import static net.zypr.maven.uotake.Uotake.weaponLoader;


public class ItemAction {

    public static void action(Player p, String action) {
        String[] params = action.split("/");
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        for (String param : params) {
            String[] args = param.split("@");
            if (args.length < 2) {
                p.sendMessage("§c無効なアクション: 管理者に問い合わせてください");
                return;
            }
            handleAction(p, playerData, args);
        }
    }

    private static void handleAction(Player p, PlayerData playerData, String[] args) {
        switch (args[0]) {
            case "server":
                handleServerAction(p, args[1]);
                break;
            case "OpenMenu":
                handleOpenMenuAction(p, args[1]);
                break;
            case "buyweapon":
                handleBuyWeaponAction(p, weaponLoader.getWeaponByName(args[1]));
                break;
            case "Sound":
                handleSoundAction(p, args[1]);
                break;
            case "status":
                handleStatusAction(p, playerData);
                break;
            case "setting":
                handleSettingAction(p, playerData, args[1]);
                break;
            default:
                p.sendMessage("§c登録されていないアクションが実行されました: 管理者に問い合わせてください");
                break;
        }
    }

    private static void handleServerAction(Player p, String server) {
        Proxy.sendPlayerToServer(p, server);
    }

    private static void handleOpenMenuAction(Player p, String menu) {
        MenuName menuName = null;
        try {
            menuName = MenuName.valueOf(menu.toUpperCase());
        } catch (IllegalArgumentException e) {
            p.sendMessage("§c無効なメニュー名: 管理者に問い合わせてください");
            return;
        }
        Menu.open(p, menuName);
    }

    private static void handleBuyWeaponAction(Player p, Weapon weapon) {
       int buy = weapon.buy(p);
        switch (buy) {
            case 0:
                p.sendMessage("§a購入成功。");
                p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
                break;
            case 1:
                p.sendMessage("§c所持金が不足しているため購入できません。");
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                break;
            case 2:
                p.sendMessage("§cすでに所持しているため購入できません。");
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                break;
            case 3:
                p.sendMessage("§c武器のデータが存在しません。管理者に問い合わせてください。");
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                break;
            case 5:
                p.sendMessage("§c前のTierの武器を開放してください。");
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                break;
            default:
                p.sendMessage("§c予期しないエラーが発生しました。管理者に問い合わせてください。");
                p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                break;
        }
    }

    private static void handleSoundAction(Player p, String soundParams) {
        String[] params = soundParams.split(",");
        if (params.length != 3) {
            p.sendMessage("§c無効なサウンドパラメータ: 管理者に問い合わせてください");
            return;
        }
        p.playSound(p, params[0], Float.parseFloat(params[1]), Float.parseFloat(params[2]));
    }

    private static void handleStatusAction(Player p, PlayerData playerData) {
        int kills = playerData.getBattleStatus().getKills();
        int deaths = playerData.getBattleStatus().getDeaths();
        int kd = (deaths == 0) ? (kills == 0 ? 0 : 99999) : kills / deaths;
        p.sendMessage("§f§m┌§f======§d【" + p.getDisplayName() + " のステータス情報】§f========",
                "§f|| §aキル数: " + kills + "    §cデス数: " + deaths + "    §5KD: " + kd,
                "§f|| §6階級: §f" + Uotake.config.getString("rank." + playerData.getRank() + ".name") + "§f[" + playerData.getRank() + "§f]    §4BonusPoint: " + playerData.getBattleStatus().getBonusPoints(),
                "§f|| §f所持金: §e" + playerData.getMoney(),
                "§f|| §7移動速度: 0    筋力: 0    防御力: 0",
                "§f|| §7連射速度: 0    リロード速度: 0    命中精度: 0",
                "§f§m└§f============================");
    }

    private static void handleSettingAction(Player p, PlayerData playerData, String settingParams) {
        String[] params = settingParams.split("\\.");
        switch (params[0]) {
            case "equip":
                handleEquipSetting(p, playerData, params);
                break;
            case "select":
                handleSelectSetting(p, playerData, params);
                break;
            case "armbool":
                if (params.length == 2) {
                    playerData.setArmorBoolean(!playerData.getArmorBoolean());
                }
                break;
            default:
                p.sendMessage("§c無効な設定パラメータ: 管理者に問い合わせてください");
                break;
        }
    }

    private static void handleEquipSetting(Player p, PlayerData playerData, String[] params) {
        if (params.length == 2) {
            playerData.setSelect(params[1]);
        } else if (params.length == 3) {
            p.sendMessage(params);
            //Menu.open(p, "equip." + params[1] + "." + params[2]);
        }
    }

    private static void handleSelectSetting(Player p, PlayerData playerData, String[] params) {
        if (params.length != 4) {
            p.sendMessage("§c無効な選択パラメータ: 管理者に問い合わせてください");
            return;
        }
        String selector = params[1];
        WeaponCategory category = WeaponCategory.valueOf(params[2].toUpperCase());
        String item = params[3];
        if (Objects.equals(selector, "a") || Objects.equals(selector, "b")) {
            Weapon weapon = weaponLoader.getWeaponByName(item);
            if (weapon != null) {
                playerData.getEquipment().get(selector).put(category, weapon);
                Menu.open(p, MenuName.EQUIP_EDITOR);
                p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
            }
        } else if (Objects.equals(selector, "armor")) {
            Weapon weapon = weaponLoader.getWeaponByName(item);
            if (weapon != null) {
                playerData.getEquipment().get("armor").put(category, weapon);
                Menu.open(p, MenuName.EQUIP_EDITOR);
                p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
            }
        }
    }
}
