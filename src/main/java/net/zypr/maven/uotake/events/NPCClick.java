package net.zypr.maven.uotake.events;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.zypr.maven.uotake.classes.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCClick implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onNPCClick(PlayerInteractEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getRightClicked())) {
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
            switch(npc.getFullName()){
                case "アサルトライフル":
                    Menu.open(event.getPlayer(),"shop.ar");
                    break;
                case "ショットガン":
                    Menu.open(event.getPlayer(),"shop.sg");
                    break;
                case "サブマシンガン":
                    Menu.open(event.getPlayer(),"shop.smg");
                    break;
                case "スナイパーライフル":
                    Menu.open(event.getPlayer(),"shop.sr");
                    break;
                case "ロケットランチャー":
                    Menu.open(event.getPlayer(),"shop.rl");
                    break;
                case "ハンドガン":
                    Menu.open(event.getPlayer(),"shop.hg");
                    break;
            }
        }

    }
}
