package fr.karamouche.amongblocks.objects.tasks;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class Ordernumbers {
    private final Main main;
    private final Inventory inv;
    private final AmongPlayer aPlayer;
    private int stage;

    public Ordernumbers(AmongPlayer aPlayer, Main main){
        Random rand = new Random();
        this.main = main;
        this.stage = 0;
        this.aPlayer = aPlayer;

        Inventory inv = Bukkit.createInventory(null, 9*5, "Order numbers");
        ArrayList<ItemStack> woolsBlock = new ArrayList();

        ItemStack unItem = main.constructWool(DyeColor.YELLOW, 1, ChatColor.GOLD+"1");
        woolsBlock.add(unItem);

        ItemStack deuxItem = main.constructWool(DyeColor.BLUE, 2, ChatColor.GOLD+"2");
        woolsBlock.add(deuxItem);

        ItemStack troisItem = main.constructWool(DyeColor.LIME, 3, ChatColor.GOLD+"3");
        woolsBlock.add(troisItem);

        ItemStack quatreItem = main.constructWool(DyeColor.ORANGE, 4, ChatColor.GOLD+"4");
        woolsBlock.add(quatreItem);

        ItemStack cinqItem = main.constructWool(DyeColor.SILVER, 5, ChatColor.GOLD+"5");
        woolsBlock.add(cinqItem);

        int i;
        for(ItemStack wools : woolsBlock){
            i = rand.nextInt(9*5);
            while(inv.getItem(i) != null)
                i = rand.nextInt(9*5);
            inv.setItem(i, wools);
        }
        this.inv = inv;
    }

    public AmongPlayer getAmongPlayer(){
        return aPlayer;
    }

    public Inventory getInv(){
        return this.inv;
    }

    public void doneNumber(){
        this.stage++;
        if(stage == 5){
            this.doneTask();
        }
    }

    public int getStage(){
        return this.stage;
    }

    public void failTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage("Vous avez échoué la tâche ORDERNUMBERS !");
    }

    private void doneTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage("Vous avez réussi la tâche ORDERNUMBERS !");
        aPlayer.doneTask(TaskEnum.ORDERNUMBERS);
    }

    public void open(){
        Bukkit.getPlayer(this.getAmongPlayer().getPlayerID()).openInventory(this.getInv());
    }
}