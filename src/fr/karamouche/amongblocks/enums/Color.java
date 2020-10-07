package fr.karamouche.amongblocks.enums;

import org.bukkit.DyeColor;

public enum Color {
    RED("AmongRed", DyeColor.RED),
    BLUE("AmongBlue", DyeColor.BLUE),
    GREEN("AmongGreen", DyeColor.GREEN),
    PINK("AmongPink", DyeColor.PINK),
    ORANGE("AmongOrange", DyeColor.ORANGE),
    YELLOW("AmongYellow", DyeColor.YELLOW),
    BLACK("AmongBlack", DyeColor.BLACK),
    GRAY("AmongGray", DyeColor.GRAY),
    PURPLE("AmongPurple", DyeColor.PURPLE),
    BROWN("AmongBrown", DyeColor.BROWN),
    AQUA("AmongAqua", DyeColor.LIGHT_BLUE),
    LIME("AmongLime", DyeColor.LIME);

    String skinName;
    DyeColor dyeColor;

    Color(String skinName, DyeColor dyeColor){
        this.skinName = skinName;
        this.dyeColor = dyeColor;
    }

    public static Color get(DyeColor dyeColor) {
        for(Color color : Color.values()){
            if(color.getDyeColor().equals(dyeColor)){
                return color;
            }
        }
        return null;
    }

    public String getSkinName(){
        return this.skinName;
    }

    public DyeColor getDyeColor(){
        return this.dyeColor;
    }
}
