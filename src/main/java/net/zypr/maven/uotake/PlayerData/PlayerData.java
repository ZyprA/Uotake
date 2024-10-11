package net.zypr.maven.uotake.PlayerData;

import java.util.List;
import java.util.Map;

public class PlayerData {
    private int rank;
    private int money;
    private List<String> mainWeapons;
    private List<String> subWeapons;
    private List<String> grenades;
    private List<String> foods;
    private Map<String, Map<String, String>> equipment; // A, B, Armor
    private String select;
    private BattleStatus battleStatus;
    private boolean bloodSetting;

    public PlayerData(int rank, int money, List<String> mainWeapons, List<String> subWeapons,
                      List<String> grenades, List<String> foods,
                      Map<String, Map<String, String>> equipment, String select, BattleStatus battleStatus, boolean bloodSetting) {
        this.rank = rank;
        this.money = money;
        this.mainWeapons = mainWeapons;
        this.subWeapons = subWeapons;
        this.grenades = grenades;
        this.foods = foods;
        this.equipment = equipment;
        this.select = select;
        this.battleStatus = battleStatus;
        this.bloodSetting = bloodSetting;
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

    public List<String> getMainWeapons() {
        return mainWeapons;
    }

    public void setMainWeapons(List<String> mainWeapons) {
        this.mainWeapons = mainWeapons;
    }

    public List<String> getSubWeapons() {
        return subWeapons;
    }

    public void setSubWeapons(List<String> subWeapons) {
        this.subWeapons = subWeapons;
    }

    public List<String> getGrenades() {
        return grenades;
    }

    public void setGrenades(List<String> grenades) {
        this.grenades = grenades;
    }

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public Map<String, Map<String, String>> getEquipment() {
        return equipment;
    }

    public void setEquipment(Map<String, Map<String, String>> equipment) {
        this.equipment = equipment;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
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
}

