package fr.karamouche.amongblocks.enums;

public enum Color {
    RED("AmongRed"),
    BLUE("AmongBlue"),
    GREEN("AmongGreen"),
    PINK("AmongPink"),
    ORANGE("AmongOrange"),
    YELLOW("AmongYellow"),
    BLACK("AmongBlack"),
    GRAY("AmongGray"),
    PURPLE("AmongPurple"),
    BROWN("AmongBrown"),
    AQUA("AmongAqua"),
    LIME("AmongLime");

    String skinName;

    Color(String skinName){
        this.skinName = skinName;
    }
}
