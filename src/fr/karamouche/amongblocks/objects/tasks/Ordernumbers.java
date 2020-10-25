package fr.karamouche.amongblocks.objects.tasks;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

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

        Wool un = new Wool();
        un.setColor(DyeColor.YELLOW);
        ItemStack unItem = un.toItemStack();
        ItemMeta unItemMeta = unItem.getItemMeta();
        unItemMeta.setDisplayName(ChatColor.GOLD+"1");
        unItem.setItemMeta(unItemMeta);
        woolsBlock.add(unItem);

        Wool deux = new Wool();
        deux.setColor(DyeColor.BLUE);
        ItemStack deuxItem = deux.toItemStack();
        ItemMeta deuxItemMeta = deuxItem.getItemMeta();
        deuxItemMeta.setDisplayName(ChatColor.GOLD+"2");
        deuxItem.setItemMeta(deuxItemMeta);
        woolsBlock.add(deuxItem);

        Wool trois = new Wool();
        trois.setColor(DyeColor.LIME);
        ItemStack troisItem = trois.toItemStack();
        ItemMeta troisItemMeta = troisItem.getItemMeta();
        troisItemMeta.setDisplayName(ChatColor.GOLD+"3");
        troisItem.setItemMeta(troisItemMeta);
        woolsBlock.add(troisItem);

        Wool quatre = new Wool();
        quatre.setColor(DyeColor.ORANGE);
        ItemStack quatreItem = quatre.toItemStack();
        ItemMeta quatreItemMeta = quatreItem.getItemMeta();
        quatreItemMeta.setDisplayName(ChatColor.GOLD+"4");
        quatreItem.setItemMeta(quatreItemMeta);
        woolsBlock.add(quatreItem);

        Wool cinq = new Wool();
        cinq.setColor(DyeColor.SILVER);
        ItemStack cinqItem = cinq.toItemStack();
        ItemMeta cinqItemMeta = cinqItem.getItemMeta();
        cinqItemMeta.setDisplayName(ChatColor.GOLD+"5");
        cinqItem.setItemMeta(cinqItemMeta);
        woolsBlock.add(cinqItem);
        int i;
        for(ItemStack wools : woolsBlock){
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