package net.zypr.maven.uotake.Others;

import dev.sergiferry.playernpc.api.NPC;
import net.zypr.maven.uotake.classes.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCManager implements Listener {
    @EventHandler
    public void onNPCInteract(NPC.Events.Interact event){
        Player player = event.getPlayer();
        NPC npc = event.getNPC();
        NPC.Interact.ClickType clickType = event.getClickType();
        switch (npc.getFullID()) {
            case "playernpc.global_shopar":
                Menu.open(player,"shop.ar");
                break;
            case "playernpc.global_shopsg":
                Menu.open(player,"shop.sg");
                break;
            case "playernpc.global_shopsr":
                Menu.open(player,"shop.sr");
                break;
            case "playernpc.global_shopsmg":
                Menu.open(player,"shop.smg");
                break;
            case "playernpc.global_shoprl":
                Menu.open(player,"shop.rl");
                break;
            case "playernpc.global_shophg":
                Menu.open(player,"shop.hg");
                break;
            default:
                break;
        }
    }
}
