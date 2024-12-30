package net.zypr.maven.uotake.EquipmentData.SkillData;

public enum SkillEffect {
    SPEED("俊足"),
    DURABILITY("耐力"),
    HEALTH("体力"),
    MAIN_AMMO("メイン弾薬"),
    SUB_AMMO("サブ弾薬");

    private final String name;

    SkillEffect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
