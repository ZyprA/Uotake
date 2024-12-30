package net.zypr.maven.uotake.EquipmentData.SkillData;

public enum SkillSet {
    A("A"),
    B("B"),
    C("C");

    private final String name;

    SkillSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
