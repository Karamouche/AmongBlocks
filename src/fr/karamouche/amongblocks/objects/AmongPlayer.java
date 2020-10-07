package fr.karamouche.amongblocks.objects;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.enums.Tools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class AmongPlayer {
    private final Main main;
    private final UUID playerID;
    private Color color;

    public AmongPlayer(Main main, Player player){
        this.main = main;
        this.playerID = player.getUniqueId();
        this.color = null;
    }

    public UUID getPlayerID(){
        return this.playerID;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        Game game = main.getGame();
        if(this.getColor() != null)
            game.getColorTaken().remove(this.getColor());
        this.color = color;
        game.getColorTaken().add(color);
    }

    public void giveLobbyItems() {
        Player player = Bukkit.getPlayer(this.getPlayerID());
        Inventory inv = player.getInventory();
        inv.setItem(4, Tools.COLORPICKER.toItem());
    }
    /*RED,
    BLUE,
    GREEN,
    PINK,
    ORANGE,
    YELLOW,
    BLACK,
    GRAY,
    PURPLE,
    BROWN,
    AQUA,
    LIME;*/
}