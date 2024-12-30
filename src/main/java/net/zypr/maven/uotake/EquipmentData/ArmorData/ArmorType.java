package net.zypr.maven.uotake.EquipmentData.ArmorData;

public enum ArmorType {
    HEAD("head"),
    BODY("body"),
    LEGS("legs"),
    FOOT("foot");

    private final String name;

    private ArmorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
