package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.EquipmentData.ArmorData.Armor;
import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorType;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Result;
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

import static net.zypr.maven.uotake.Uotake.armorLoader;
import static net.zypr.maven.uotake.Uotake.weaponLoader;


public class ItemAction {

    public static void action(Player p, String action) {
        String[] params = action.split("/");
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        for (String param : params) {
            String[] args = param.split("@");
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
       Weapon.Result buy = weapon.buy(p);
        p.sendMessage(buy.getMessage());
        if (Objects.requireNonNull(buy) == Weapon.Result.SUCCESS) {
            p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
        } else {
            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
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
        String[] params = settingParams.split("_");
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
        }
    }

    private static void handleSelectSetting(Player p, PlayerData playerData, String[] params) {
        if (params.length != 4) {
            p.sendMessage("§c無効な選択パラメータ: 管理者に問い合わせてください");
            return;
        }
        String selector = params[1];
        String item = params[3];
        if (Objects.equals(selector, "a") || Objects.equals(selector, "b")) {
            WeaponCategory category = WeaponCategory.valueOf(params[2].toUpperCase());
            Weapon weapon = weaponLoader.getWeaponByName(item);
            if (weapon != null) {
                playerData.getEquipment().get(selector).put(category, weapon);
                Menu.open(p, MenuName.EQUIP_EDITOR);
                p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
            }
        } else if (Objects.equals(selector, "armor")) {
            ArmorType armorType = ArmorType.valueOf(params[2].toUpperCase());
            Armor armor = armorLoader.getArmorByName(item);
            if (armor != null) {
                playerData.getArmor().put(armorType, armor);
                Menu.open(p, MenuName.EQUIP_EDITOR);
                p.playSound(p, Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
            }
        }
    }
}
