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
    private final List<String> head;
    private final List<String> body;
    private final List<String> legs;
    private final List<String> foot;
    private Map<String, Map<String, String>> equipment; // A, B, Armor
    private String select;
    private BattleStatus battleStatus;
    private boolean bloodSetting;

    public PlayerData(int rank, int money, List<String> mainWeapons, List<String> subWeapons,
                      List<String> grenades, List<String> foods, List<String> head, List<String> body, List<String> legs, List<String> foot,
                      Map<String, Map<String, String>> equipment, String select, BattleStatus battleStatus, boolean bloodSetting) {
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
        this.select = select;
        this.battleStatus = battleStatus;
        this.bloodSetting = bloodSetting;
    }

    public List<String> getWeapons(String category) {
        switch (category) {
            case "main":
                return this.mainWeapons;
            case "sub":
                return this.subWeapons;
            case "grenade":
                return this.grenades;
            case "food":
                return this.foods;
            case "head":
                return this.head;
            case "body":
                return this.body;
            case "legs":
                return this.legs;
            case "foot":
                return this.foot;
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

    public List<String> getHead() {
        return head;
    }

    public List<String> getBody() {
        return body;
    }

    public List<String> getLegs() {
        return legs;
    }

    public List<String> getFoot() {
        return foot;
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
