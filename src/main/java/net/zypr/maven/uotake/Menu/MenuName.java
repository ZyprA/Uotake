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
    SHOP_HG("hg");

    private final String name;

    MenuName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
