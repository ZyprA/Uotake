package net.zypr.maven.uotake.classes;

import net.zypr.maven.uotake.Uotake;
import net.zypr.maven.uotake.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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

    public String[] getEquipment() {
        String select = this.userfile.getConfig().getString("equipment.select");
        return getEquipment(select != null ? select : "default");
    }

    public Integer getRank() {
        return this.userfile.getConfig().getInt("user.rank", 0);
    }

    public void setRank(Integer value) {
        this.userfile.getConfig().set("user.rank", value);
    }

    public void addRank(Integer value) {
        addUserValue("user.rank", value);
    }

    public Integer getMoney() {
        return this.userfile.getConfig().getInt("user.money", 0);
    }

    public void setMoney(Integer value) {
        this.userfile.getConfig().set("user.money", value);
    }

    public void addMoney(Integer value) {
        addUserValue("user.money", value);
    }

    private void addUserValue(String key, Integer value) {
        int currentValue = this.userfile.getConfig().getInt(key, 0);
        this.userfile.getConfig().set(key, currentValue + value);
    }

    public void saveConfig() {
        this.userfile.save();
    }

    public String[] getEquipment(@NotNull String select) {
        String[] data = new String[4];
        try {
            data[0] = this.userfile.getConfig().getString("equipment." + select + ".main", "none");
            data[1] = this.userfile.getConfig().getString("equipment." + select + ".sub", "none");
            data[2] = this.userfile.getConfig().getString("equipment." + select + ".grenade", "none");
            data[3] = this.userfile.getConfig().getString("equipment." + select + ".food", "none");
        } catch (Exception e) {
            this.player.sendMessage("装備の取得中にエラーが発生しました: " + e.getMessage());
        }
        return data;
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
