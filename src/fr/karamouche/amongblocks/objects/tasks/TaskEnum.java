package fr.karamouche.amongblocks.objects.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public enum TaskEnum {
    //FAIRE UN CODE ECRIT SUR UN PAPIER
    DIGIT(new Location(Bukkit.getWorld("TheSkeld"), 742, 13, -514), Material.JUKEBOX),

    //CLICKER SUR DES CUBS DANS L'ORDRE
    ORDERNUMBERS(new Location(Bukkit.getWorld("TheSkeld"), 747, 13, -516), Material.RED_MUSHROOM),

    //TRIER LES CUBES DANS LA BONNE CASE
    CUBEORGANIZER(new Location(Bukkit.getWorld("TheSkeld"), 744, 13, -523), Material.MINECART),

    //RELIER LES DEUX COTES DE BONNE COULEUR
    WIRE(new Location(Bukkit.getWorld("TheSkeld"), 751, 13, -525), Material.SUGAR_CANE),

    //CLICKER ET ATTENDRE
    UPLOAD(new Location(Bukkit.getWorld("TheSkeld"), 758, 13, -521), Material.REDSTONE_COMPARATOR),

    //CLICKER SUR LES PAIRES D'OBJETS
    PAIR(new Location(Bukkit.getWorld("TheSkeld"), 761, 13, -515), Material.POWERED_RAIL),

    //CLICKER SUR LE BON BOUTON
    CLICKONGOOD(new Location(Bukkit.getWorld("TheSkeld"), 755, 13, -514), Material.REDSTONE);

    private final Location loc;
    private final Material mat;

    TaskEnum(Location loc, Material mat) {
        this.loc = loc;
        this.mat = mat;
    }

    public Location toLocation(){
        return this.loc;
    }

    public Material getMat(){
        return this.mat;
    }
}
