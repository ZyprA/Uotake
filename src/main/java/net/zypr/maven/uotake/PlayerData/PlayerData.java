package net.zypr.maven.uotake.PlayerData;

import net.zypr.maven.uotake.EquipmentData.ArmorData.Armor;
import net.zypr.maven.uotake.EquipmentData.ArmorData.ArmorType;
import net.zypr.maven.uotake.EquipmentData.WeaponData.Weapon;
import net.zypr.maven.uotake.EquipmentData.WeaponData.WeaponCategory;

import java.util.List;
import java.util.Map;

public class PlayerData {
    private int rank;
    private int money;
    private List<Weapon> mainWeapons;
    private List<Weapon> subWeapons;
    private List<Weapon> grenades;
    private List<Weapon> foods;
    private final List<Armor> head;
    private final List<Armor> body;
    private final List<Armor> legs;
    private final List<Armor> foot;
    private Map<String, Map<WeaponCategory, Weapon>> equipment; // A, B
    private Map<ArmorType, Armor> armor;
    private String select;
    private Boolean armorboolean;
    private BattleStatus battleStatus;
    private boolean bloodSetting;

    public PlayerData(int rank, int money, List<Weapon> mainWeapons, List<Weapon> subWeapons,
                      List<Weapon> grenades, List<Weapon> foods, List<Armor> head, List<Armor> body, List<Armor> legs, List<Armor> foot,
                      Map<String, Map<WeaponCategory, Weapon>> equipment, Map<ArmorType, Armor> armor, String select, Boolean armorboolean, BattleStatus battleStatus, boolean bloodSetting) {
        this.rank = rank;
        this.money = money;
        this.mainWeapons = mainWeapons;
        this.subWeapons = subWeapons;
        this.grenades = grenades;
        this.foods = foods;
        this.head = head;
        this.body = body;
        this.legs = legs;
        this.foot = foot;
        this.equipment = equipment; // 装備中のものを示す
        this.armor = armor;
        this.armorboolean = armorboolean;
        this.select = select;
        this.battleStatus = battleStatus;
        this.bloodSetting = bloodSetting;
    }

    public List<Weapon> getWeapons(WeaponCategory category) {
        switch (category) {
            case MAIN:
                return this.mainWeapons;
            case SUB:
                return this.subWeapons;
            case GRENADE:
                return this.grenades;
            case FOOD:
                return this.foods;
            default:
                return null;
        }
    }

    // ゲッターとセッター
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Weapon> getMainWeapons() {
        return mainWeapons;
    }

    public void setMainWeapons(List<Weapon> mainWeapons) {
        this.mainWeapons = mainWeapons;
    }

    public List<Weapon> getSubWeapons() {
        return subWeapons;
    }

    public void setSubWeapons(List<Weapon> subWeapons) {
        this.subWeapons = subWeapons;
    }

    public List<Weapon> getGrenades() {
        return grenades;
    }

    public void setGrenades(List<Weapon> grenades) {
        this.grenades = grenades;
    }

    public List<Weapon> getFoods() {
        return foods;
    }

    public void setFoods(List<Weapon> foods) {
        this.foods = foods;
    }

    public List<Armor> getHead() {
        return head;
    }

    public List<Armor> getBody() {
        return body;
    }

    public List<Armor> getLegs() {
        return legs;
    }

    public List<Armor> getFoot() {
        return foot;
    }

    public Map<String, Map<WeaponCategory, Weapon>> getEquipment() {
        return equipment;
    }

    public void setEquipment(Map<String, Map<WeaponCategory, Weapon>> equipment) {
        this.equipment = equipment;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Boolean getArmorBoolean() {
        return armorboolean;
    }

    public void setArmorBoolean(Boolean armorboolean) {
        this.armorboolean = armorboolean;
    }

    public BattleStatus getBattleStatus() {
        return battleStatus;
    }

    public void setBattleStatus(BattleStatus battleStatus) {
        this.battleStatus = battleStatus;
    }

    public boolean isBloodSetting() {
        return bloodSetting;
    }

    public void setBloodSetting(boolean bloodSetting) {
        this.bloodSetting = bloodSetting;
    }

    public Map<ArmorType, Armor> getArmor() {
        return armor;
    }

    public void setArmor(Map<ArmorType, Armor> armor) {
        this.armor = armor;
    }
}
