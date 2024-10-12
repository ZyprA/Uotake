package net.zypr.maven.uotake.events;

import net.citizensnpcs.api.event.NPCClickEvent;
import net.zypr.maven.uotake.classes.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCClick implements Listener {
    @EventHandler
    public void onNPCClick(NPCClickEvent event) {
        event.getClicker().sendMessage(event.getNPC().getFullName());
        switch(event.getNPC().getFullName()){
            case "アサルトライフル":
                Menu.open(event.getClicker(),"shop.ar");
                break;
            case "ショットガン":
                Menu.open(event.getClicker(),"shop.sg");
                break;
            case "サブマシンガン":
                Menu.open(event.getClicker(),"shop.smg");
                break;
            case "スナイパーライフル":
                Menu.open(event.getClicker(),"shop.sr");
                break;
            case "ロケットランチャー":
                Menu.open(event.getClicker(),"shop.rl");
                break;
            case "ハンドガン":
                Menu.open(event.getClicker(),"shop.hg");
                break;
        }
    }
}
