package net.zypr.maven.uotake.EquipmentData.WeaponData;

public enum WeaponType {

    AR("ar"),
    SMG("smg"),
    SR("sr"),
    SG("sg"),
    HG("hg"),
    RL("rl"),
    GRENADE("grenade"),
    FOOD("food");

    private final String name;

    WeaponType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
