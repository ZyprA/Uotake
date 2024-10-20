package net.zypr.maven.uotake.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class ItemCreator {

    private Material material;
    private int amount;
    private byte data;
    private String name;
    private List<String> lore;
    private Map<String,String> nbt;
    private Integer cmd;
    private String action;

    public ItemCreator() {
        this.material = null;
        this.data = 0;
        this.name = null;
        this.lore = null;
        this.nbt = null;
        this.amount = 1;
        this.cmd = null;
        this.action = null;
    }

    public ItemStack generate() {
        ItemStack itemStack = new ItemStack(this.material,this.amount,this.data);
        if (this.action != null) {
            NBTAPI.addNBT(itemStack, "action", this.action);
        }
        if (this.nbt != null) {
            for (Map.Entry<String, String> entry : this.nbt.entrySet()) {
                NBTAPI.addNBT(itemStack, entry.getKey(), entry.getValue());
            }
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(this.name);
            meta.setLore(this.lore);
            meta.setCustomModelData(cmd);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void reset() {
        this.material = null;
        this.data = 0;
        this.name = null;
        this.lore = null;
        this.nbt = null;
        this.amount = 1;
        this.cmd = null;
        this.action = null;
    }


    public ItemCreator setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemCreator setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemCreator setData(byte data) {
        this.data = data;
        return this;
    }

    public ItemCreator setName(String name) {
        this.name = name;
        return this;
    }

    public ItemCreator setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemCreator setNbt(Map<String, String> nbt) {
        this.nbt = nbt;
        return this;
    }

    public ItemCreator setCmd(Integer cmd) {
        this.cmd = cmd;
        return this;
    }

    public ItemCreator setAction(String action) {
        this.action = action;
        return this;
    }
}
