package net.zypr.maven.uotake.EquipmentData.WeaponData;

public enum Result {

    SUCCESS("§a購入に成功しました"),
    ALREADY_HAVE("§c既に所持しています"),
    NOT_ENOUGH_MONEY("§cお金が足りません"),
    NOT_ENOUGH_TIER("§c前のTierの武器を全開放してください"),
    SOMETHING_ERROR("§c何かしらのエラーが発生しました");

    private final String message;

    Result(String message) {
        this.message = message;
    }
}
