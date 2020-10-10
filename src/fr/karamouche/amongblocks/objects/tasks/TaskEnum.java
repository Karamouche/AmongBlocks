package fr.karamouche.amongblocks.objects.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum TaskEnum {
    DIGIT(new Location(Bukkit.getWorld("TheSkeld"), 742, 13, -514)),
    ORDERNUMBERS(new Location(Bukkit.getWorld("TheSkeld"), 747, 13, -516)),
    CUBEORGANIZER(new Location(Bukkit.getWorld("TheSkeld"), 744, 13, -523)),
    WIRE(new Location(Bukkit.getWorld("TheSkeld"), 751, 13, -525)),
    UPLOAD(new Location(Bukkit.getWorld("TheSkeld"), 758, 13, -521)),
    PAIR(new Location(Bukkit.getWorld("TheSkeld"), 761, 13, -515)),
    CLICKONGOOD(new Location(Bukkit.getWorld("TheSkeld"), 755, 13, -514));

    private final Location loc;

    TaskEnum(Location loc) {
        this.loc = loc;
    }

    public Location toLocation(){
        return this.loc;
    }
}
