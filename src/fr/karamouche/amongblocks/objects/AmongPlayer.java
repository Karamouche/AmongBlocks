package fr.karamouche.amongblocks.objects;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.enums.Roles;
import fr.karamouche.amongblocks.enums.Tools;
import fr.karamouche.amongblocks.objects.tasks.Digit;
import fr.karamouche.amongblocks.objects.tasks.Ordernumbers;
import fr.karamouche.amongblocks.objects.tasks.TaskEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.UUID;

public class AmongPlayer {
    private final Main main;
    private final UUID playerID;
    private Color color;
    private final ArrayList<TaskEnum> tasksList;
    private final ArrayList<TaskEnum> doneTasks;
    private Object actualTask;

    private Roles role;

    public AmongPlayer(Main main, Player player){
        this.main = main;
        this.playerID = player.getUniqueId();
        this.color = null;
        this.tasksList = new ArrayList<>();
        this.doneTasks = new ArrayList<>();
        this.role = null;
        this.actualTask = null;
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

    public void setActualTask(Object task){
        this.actualTask = task;
    }

    public Object getActualTask(){
        return this.actualTask;
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

    public void annonceRole() {
        //ANNONCE SONT ROLE AU JOUEUR
        Player player = Bukkit.getPlayer(this.getPlayerID());
        PotionEffect potionEffect= new PotionEffect(PotionEffectType.BLINDNESS, 300, 1, true, false);
        player.addPotionEffect(potionEffect);
        player.sendTitle(this.getRole().getColor() + this.getRole().toString(), "Il y a "+ ChatColor.RED + "1"+ ChatColor.WHITE+" imposteur dans la partie");
    }

    public void giveItems(){
        Player player = Bukkit.getPlayer(this.getPlayerID());
        if(this.getRole().equals(Roles.CREWMATE)){
            player.getInventory().setItem(8, Tools.TRACKER.toItem());
        }else if(this.getRole().equals(Roles.IMPOSTER)){
            player.getInventory().setItem(8, Tools.KILL.toItem());
        }
    }

    public void playTask(TaskEnum task){
        if(task !=null){
            if(task.equals(TaskEnum.DIGIT)){
                Digit digit = new Digit(this, main);
                digit.open();
                this.setActualTask(digit);
            }else if(task.equals(TaskEnum.ORDERNUMBERS)){
                Ordernumbers ordernumbers = new Ordernumbers(this, main);
                ordernumbers.open();
                this.setActualTask(ordernumbers);
            }
        }
    }

    public void doneTask(TaskEnum task) {
        this.getTasks().remove(task);
        this.getDoneTasks().add(task);
    }

    public void openTaskTracker() {
        Inventory inv = Bukkit.createInventory(null, 9, "Vos tasks");
        int i = 0;
        for(TaskEnum taskEnum : this.getTasks()){
            ItemStack itemStack = new ItemStack(taskEnum.getMat());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD+taskEnum.toString().toLowerCase());
            itemStack.setItemMeta(itemMeta);
            inv.setItem(i, itemStack);
            i++;
        }
        Bukkit.getPlayer(this.getPlayerID()).openInventory(inv);
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
