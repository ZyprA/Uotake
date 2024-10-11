package net.zypr.maven.uotake.util;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {

    // プレイヤーごとのクールダウンを管理するHashMap
    private static HashMap<UUID, Long> cooldowns = new HashMap<>();

    /**
     * プレイヤーのクールダウンがアクティブかどうかを確認するメソッド
     * @param playerId プレイヤーのUUID
     * @param cooldownTime クールダウンの間隔（ミリ秒）
     * @return クールダウン中であればtrue、そうでなければfalse
     */
    public static boolean isCooldownActive(UUID playerId, long cooldownTime) {
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId)) {
            long lastClickTime = cooldowns.get(playerId);
            // クールダウン中かどうかを確認
            return currentTime - lastClickTime < cooldownTime;
        }
        return false;
    }

    /**
     * プレイヤーにクールダウンを設定するメソッド
     * @param playerId プレイヤーのUUID
     */
    public static void setCooldown(UUID playerId) {
        long currentTime = System.currentTimeMillis();
        cooldowns.put(playerId, currentTime);
    }
}

