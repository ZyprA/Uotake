package net.zypr.maven.uotake.EquipmentData.SkillData;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class SkillLoader {
    private final Map<String, Skill> skillMap = new HashMap<>();

    public void loadSkills(FileConfiguration config) {

        skillMap.clear();
        ConfigurationSection skillSection = config.getConfigurationSection("skills");

        if (skillSection == null) {
            return;
        }

        for (String key : skillSection.getKeys(false)) {
            ConfigurationSection skillData = skillSection.getConfigurationSection(key);

            if (skillData != null) {
                String name = skillData.getString("name");
                int cost = skillData.getInt("cost", -1);
                int tier = skillData.getInt("tier", -1);
                Map<SkillEffect, Float> effects = new HashMap<>();

                for (String effectKey : skillData.getConfigurationSection("effects").getKeys(false)) {
                    double value = skillData.getConfigurationSection("effects").getDouble(effectKey);
                    SkillEffect effect = SkillEffect.valueOf(effectKey.toUpperCase());
                    effects.put(effect, (float) value);
                }


                if (cost != -1 && tier != -1) {
                    Skill skill = new Skill();
                    skill.setId(key);
                    skill.setName(name);
                    skill.setCost(cost);
                    skill.setTier(tier);
                    skill.setEffects(effects);

                    skillMap.put(key, skill);
                }
            }
        }
    }

    public Skill getSkillById(String id) {
        return skillMap.get(id);
    }

    public Skill getSkill(String id) {
        return skillMap.get(id);
    }


}
