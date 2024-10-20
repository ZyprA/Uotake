package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Menu.Menu;
import net.zypr.maven.uotake.Others.Proxy;
import net.zypr.maven.uotake.PlayerData.PlayerData;
import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;


public class ItemAction {

    public static void action(Player p, String action) {
        String[] params = action.split("/");
        PlayerData playerData = Uotake.playerDataManager.getPlayerData(p.getUniqueId());
        for (String param :params) {
            String[] args = param.split("@");
            switch (args[0]) {
                case "server":
                    if (args.length != 2) {return;}
                    Proxy.sendPlayerToServer(p,args[1]);
                    break;
                case "OpenMenu":
                    if (args.length != 2) {return;}
                    Menu.open(p, args[1]);
                    break;
                case "buyweapon":
                    if (args.length != 2) {return;}
                    Integer buy = Weapon.buyWeapon(p, args[1]);
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
                        default:
                            p.sendMessage("§c予期しないエラーが発生しました。管理者に問い合わせてください。");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            break;
                    }
                    break;
                case "Sound":
                    if (args.length != 2) {return;}
                    String[] soundParams = args[1].split(",");
                    if (soundParams.length != 3) {return;}
                    p.playSound(p, soundParams[0], Float.parseFloat(soundParams[1]),Float.parseFloat(soundParams[2]));
                    break;
                case "status":
                    int kills = playerData.getBattleStatus().getKills();
                    int deaths = playerData.getBattleStatus().getDeaths();
                    int kd = (deaths == 0) ? (kills == 0 ? 0 : 99999) : 0;
                    p.sendMessage("§f§m┌§f======§d【" + p.getDisplayName() + " のステータス情報】§f========",
                            "§f|| §aキル数: " + kills + "    §cデス数: " + deaths + "    §5KD: " + kd,
                            "§f|| §6階級: §f" + Uotake.config.getString("rank." + playerData.getRank() + ".name") + "§f[" + playerData.getRank() + "§f]    §4BonusPoint: " + playerData.getBattleStatus().getBonusPoints(),
                            "§f|| §f所持金: §e" + playerData.getMoney(),
                            "§f|| §7移動速度: 0    筋力: 0    防御力: 0",
                            "§f|| §7連射速度: 0    リロード速度: 0    命中精度: 0",
                            "§f§m└§f============================");
                    break;
                case "setting":
                    String[] settingParams = args[1].split("\\.");
                    if (Objects.equals(settingParams[0], "equip")) {
                        if (settingParams.length == 2) {
                            playerData.setSelect(settingParams[1]);
                        } else if (settingParams.length == 3) {
                            Menu.open(p,args[1]);

                        }
                    } else if (Objects.equals(settingParams[0], "select")) {
                        if (settingParams.length == 4) {
                            String selector = settingParams[1];
                            if (Objects.equals(selector, "a") || Objects.equals(selector, "b")) {
                                String category = settingParams[2];
                                String weapon = settingParams[3];
                                if(Weapon.ifExists(weapon,category)) {
                                    playerData.getEquipment().get(selector).put(category,weapon);
                                    Menu.open(p,"equipeditor");
                                    p.playSound(p,Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,1f,1f);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;

            }
        }
    }
}
