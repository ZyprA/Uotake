package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.util.ConfigUtil;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class User {
    private final Player player;
    private final ConfigUtil userfile;

    public User(Player p) {
        this.player = p;
        this.userfile = new ConfigUtil("/users/" + this.player.getUniqueId() + ".yml");
    }

    public boolean isExistData() {
        return this.userfile.getFile().exists();
    }

    public void arrangeData() {
        if (!isExistData()) {
            tryCreatePlayerFile();
        }

        List<String> contents = (List<String>) Uotake.config.getList("load_contents.contents");
        if (contents != null) {
            for (String index : contents) {
                if (!this.userfile.getConfig().isSet(index)) {
                    Object value = Uotake.config.get("load_contents.settings." + index);
                    this.userfile.getConfig().set(index, value);
                    this.player.sendMessage(index + "を追加しました。: " + value);
                }
            }
        }
        this.userfile.save();
    }

    private void tryCreatePlayerFile() {
        try {
            if (!this.userfile.getFile().exists()) {
                this.userfile.getFile().createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("プレイヤーファイルの作成中にエラーが発生しました: " + e.getMessage(), e);
        }
    }
}
