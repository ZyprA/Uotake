package net.zypr.maven.uotake.EquipmentData.SkillData;

public enum SkillEffect {
    SPEED("俊足"),
    DURABILITY("耐力"),
    JUMP("飛躍"),
    HEALTH("体力"),
    AMMO("弾薬");

    private final String name;

    SkillEffect(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
