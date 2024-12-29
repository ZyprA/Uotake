package net.zypr.maven.uotake.events;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.zypr.maven.uotake.Menu.Menu;
import net.zypr.maven.uotake.Menu.MenuName;
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
                    Menu.open(event.getPlayer(), MenuName.SHOP_AR);
                    break;
                case "ショットガン":
                    Menu.open(event.getPlayer(), MenuName.SHOP_SG);
                    break;
                case "サブマシンガン":
                    Menu.open(event.getPlayer(), MenuName.SHOP_SMG);
                    break;
                case "スナイパーライフル":
                    Menu.open(event.getPlayer(), MenuName.SHOP_SR);
                    break;
                case "ロケットランチャー":
                    Menu.open(event.getPlayer(), MenuName.SHOP_RL);
                    break;
                case "ハンドガン":
                    Menu.open(event.getPlayer(), MenuName.SHOP_HG);
                    break;
            }
        }

    }
}
