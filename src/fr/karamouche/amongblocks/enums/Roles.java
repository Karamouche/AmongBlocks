package fr.karamouche.amongblocks.enums;

import org.bukkit.ChatColor;

public enum Roles {
    CREWMATE,
    IMPOSTER;

    public ChatColor getColor(){
        if(this.equals(CREWMATE)){
            return ChatColor.AQUA;
        }
        else
            return ChatColor.RED;
    }
}
