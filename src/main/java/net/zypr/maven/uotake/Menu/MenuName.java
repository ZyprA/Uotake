package net.zypr.maven.uotake.Menu;

public enum MenuName {
    GAMES_SELECT("gameselect"),
    MAIN_MENU("mainmenu"),
    EQUIP_EDITOR("equipeditor"),
    SHOP_AR("ar"),
    SHOP_SG("sg"),
    SHOP_SMG("smg"),
    SHOP_SR("sr"),
    SHOP_RL("rl"),
    SHOP_HG("hg"),
    EQUIP_A_MAIN("equip.a.main"),
    EQUIP_A_SUB("equip.a.sub"),
    EQUIP_A_GRENADE("equip.a.grenade"),
    EQUIP_A_FOOD("equip.a.food"),
    EQUIP_B_MAIN("equip.b.main"),
    EQUIP_B_SUB("equip.b.sub"),
    EQUIP_B_GRENADE("equip.b.grenade"),
    EQUIP_B_FOOD("equip.b.food"),
    EQUIP_ARMOR_HEAD("equip.armor.head"),
    EQUIP_ARMOR_BODY("equip.armor.body"),
    EQUIP_ARMOR_LEGS("equip.armor.legs"),
    EQUIP_ARMOR_FOOT("equip.armor.foot");
    ;

    private final String name;

    MenuName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
