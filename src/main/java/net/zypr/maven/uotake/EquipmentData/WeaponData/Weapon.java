package net.zypr.maven.uotake.EquipmentData.WeaponData;

import net.zypr.maven.uotake.PlayerData.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

import static net.zypr.maven.uotake.Uotake.playerDataManager;
import static net.zypr.maven.uotake.Uotake.weaponLoader;

public class Weapon {
    private String id;
    private String name;
    private String wm_id;
    private int cost;
    private int tier;
    private List<String> description;
    private WeaponCategory category;
    private WeaponType type;
    private int ammo;
    private int amount;
    private int cmd;
    private Material material;

    // Getters and setters
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

    public String getWm_id() {
        return wm_id;
    }

    public void setWm_id(String wm_id) {
        this.wm_id = wm_id;
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

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public WeaponCategory getCategory() {
        return category;
    }

    public void setCategory(WeaponCategory category) {
        this.category = category;
    }

    public WeaponType getType() {
        return type;
    }

    public void setType(WeaponType type) {
        this.type = type;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean ifPlayerHas(Player p) {
        List<Weapon> weapons = playerDataManager.getPlayerData(p.getUniqueId()).getWeapons(this.category);
        return weapons != null && weapons.contains(this);
    }

    public boolean ifPlayerIsAbleToBuyByTier(Player p) {
        List<Weapon> weapons = weaponLoader.getWeaponsByType(this.getType());
        return weapons.stream()
                .filter(weapon -> weapon.getTier() < this.getTier())
                .allMatch(weapon -> weapon.ifPlayerHas(p));
    }

    public Result buy(Player p) {
        if (ifPlayerHas(p)) {
            return Result.ALREADY_HAVE;
        }
        if (!ifPlayerIsAbleToBuyByTier(p)) {
            return Result.NOT_ENOUGH_TIER;
        }
        PlayerData playerData = playerDataManager.getPlayerData(p.getUniqueId());
        if (playerData.getMoney() >= this.getCost()) {
            playerData.getWeapons(this.category).add(this);
            playerData.setMoney(playerData.getMoney() - this.getCost());
            return Result.SUCCESS;
        }
        return Result.NOT_ENOUGH_MONEY;
    }

    public enum Result {

        SUCCESS("§a購入に成功しました"),
        ALREADY_HAVE("§c既に所持しています"),
        NOT_ENOUGH_MONEY("§cお金が足りません"),
        NOT_ENOUGH_TIER("§c前のTierの武器を全開放してください"),
        ;

        private final String message;

        Result(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}

