package fr.karamouche.amongblocks.enums;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum Color {
    RED("AmongRed", DyeColor.RED, ChatColor.RED),
    BLUE("AmongBlue", DyeColor.BLUE, ChatColor.BLUE),
    GREEN("AmongGreen", DyeColor.GREEN, ChatColor.DARK_GREEN),
    PINK("AmongPink", DyeColor.PINK, ChatColor.LIGHT_PURPLE),
    ORANGE("AmongOrange", DyeColor.ORANGE, ChatColor.GOLD),
    YELLOW("AmongYellow", DyeColor.YELLOW, ChatColor.YELLOW),
    BLACK("AmongBlack", DyeColor.BLACK, ChatColor.BLACK),
    GRAY("AmongGray", DyeColor.GRAY, ChatColor.GRAY),
    PURPLE("AmongPurple", DyeColor.PURPLE, ChatColor.DARK_PURPLE),
    BROWN("AmongBrown", DyeColor.BROWN, ChatColor.DARK_RED),
    AQUA("AmongAqua", DyeColor.LIGHT_BLUE, ChatColor.AQUA),
    LIME("AmongLime", DyeColor.LIME, ChatColor.GREEN);

    String skinName;
    DyeColor dyeColor;
    ChatColor color;

    Color(String skinName, DyeColor dyeColor, ChatColor color){
        this.skinName = skinName;
        this.dyeColor = dyeColor;
        this.color = color;
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

    public ChatColor getColor(){
        return this.color;
    }
}
