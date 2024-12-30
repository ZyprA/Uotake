package net.zypr.maven.uotake.EquipmentData.SkillData;

import java.util.Map;

public class Skill {
    private String id;
    private String name;
    private int cost;
    private int tier;
    private Map<SkillEffect, Float> effects;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public Map<SkillEffect, Float> getEffects() {
        return effects;
    }

    public void setEffects(Map<SkillEffect, Float> effects) {
        this.effects = effects;
    }
}
