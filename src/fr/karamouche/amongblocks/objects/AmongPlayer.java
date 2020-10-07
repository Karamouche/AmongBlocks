package fr.karamouche.amongblocks.objects;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.enums.Roles;
import fr.karamouche.amongblocks.enums.Tools;
import fr.karamouche.amongblocks.objects.tasks.TaskEnum;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.UUID;

public class AmongPlayer {
    private final Main main;
    private final UUID playerID;
    private Color color;
    private final ArrayList<TaskEnum> tasksList;
    private final ArrayList<TaskEnum> doneTasks;
    private Roles role;

    public AmongPlayer(Main main, Player player){
        this.main = main;
        this.playerID = player.getUniqueId();
        this.color = null;
        this.tasksList = new ArrayList<>();
        this.doneTasks = new ArrayList<>();
        this.role = null;
    }

    public UUID getPlayerID(){
        return this.playerID;
    }

    public Color getColor(){
        return this.color;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setColor(Color color){
        Game game = main.getGame();
        if(this.getColor() != null)
            game.getColorTaken().remove(this.getColor());
        this.color = color;
        game.getColorTaken().add(color);
    }

    //TASK MANAGER
    public ArrayList<TaskEnum> getTasks(){
        return this.tasksList;
    }

    public ArrayList<TaskEnum> getDoneTasks() {
        return doneTasks;
    }

    public void hasDoneTask(TaskEnum task){
        this.getDoneTasks().add(task);
        main.getGame().addTaskDone();
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
