package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.WeaponData.Weapon;
import net.zypr.maven.uotake.util.PlaceHolder;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import java.util.Objects;


public class ItemAction {

    public static void action(Player p, String action) {
        String[] params = action.split("/");
        for (String param :params) {
            String[] args = param.split("@");
            switch (args[0]) {
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
                            p.playSound(p.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1f, 1f);
                            break;
                        case 1:
                            p.sendMessage("§c所持金が不足しているため購入できません。");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            break;
                        case 2:
                            p.sendMessage("§cすでに所持しているため購入できません。");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            break;
                        case 3:
                            p.sendMessage("§c武器のデータが存在しません。管理者に問い合わせてください。");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            break;
                        default:
                            p.sendMessage("§c予期しないエラーが発生しました。管理者に問い合わせてください。");
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1f, 1f);
                            break;
                    }
                case "Sound":
                    if (args.length != 2) {return;}
                    String[] soundParams = args[1].split(",");
                    if (soundParams.length != 3) {return;}
                    p.playSound(p.getLocation(), soundParams[0], Float.parseFloat(soundParams[1]),Float.parseFloat(soundParams[2]));
                    break;
                case "status":
                    if (Uotake.config.contains("display.status")) {
                        for (Object o : Objects.requireNonNull(Uotake.config.getList("display.status"))) {
                            p.sendMessage(PlaceHolder.r((String) o, p, "player"));
                        }
                    }
                    break;
                case "setting":
                    String[] settingParams = args[1].split("\\.");
                    if (Objects.equals(settingParams[0], "equip")) {
                        if (settingParams.length == 2) {
                            Uotake.playerDataManager.getPlayerData(p.getUniqueId()).setSelect(settingParams[1]);
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
                                Uotake.playerDataManager.getPlayerData(p.getUniqueId()).getEquipment().get(selector).put(category,weapon);
                                Menu.open(p,"equipeditor");
                                p.playSound(p,Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR,1f,1f);
                            }
                        }
                    }
                }
                default:
                    break;

            }
        }
    }
}
