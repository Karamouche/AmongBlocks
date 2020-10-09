package fr.karamouche.amongblocks.enums;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Spawn {
    SPAWN(new Location(Bukkit.getWorld("TheSkeld"), 748, 13, -506));

    private final Location loc;

    Spawn(Location loc) {
        this.loc = loc;
    }

    public Location getLoc() {
        return this.loc;
    }
}
