package net.zypr.maven.uotake.EquipmentData.WeaponData;

public enum WeaponCategory {
    MAIN("main"),
    SUB("sub"),
    GRENADE("grenade"),
    FOOD("food");

    private final String name;

    WeaponCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
