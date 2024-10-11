package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Objects;


public class ItemAction {

    public static void action(Player p, String action) {
        String[] params = action.split("/");
        for (String param :params) {
            String[] args = param.split("@");
            switch (args[0]) {
                case "OpenMenu":
                    Menu.open(p, args[1]);
                    break;
                case "Sound":
                    String[] soundParams = args[1].split(",");
                    p.playSound(p.getLocation(), soundParams[0], Float.parseFloat(soundParams[1]),Float.parseFloat(soundParams[2]));
                    break;
                case "status":
                    if (Uotake.config.contains("display.status")) {
                        Iterator it = Uotake.config.getList("display.status").iterator();
                        while(it.hasNext()) {
                            p.sendMessage(PlaceHolder.r((String) it.next(),p,"player"));
                        }
                    }
                    break;
                case "setting":
                    String[] settingParams = args[1].split("\\.");
                    if (Objects.equals(settingParams[0], "equip")) {
                        if (settingParams.length == 2) {
                            Uotake.playerDataManager.getPlayerData(p.getUniqueId()).setSelect(settingParams[1]);
                        }
                    }
                default:
                    break;

            }
        }
    }
}
