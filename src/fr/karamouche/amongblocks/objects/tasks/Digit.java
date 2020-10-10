package fr.karamouche.amongblocks.objects.tasks;

import fr.karamouche.amongblocks.objects.AmongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class Digit {
    private final Inventory inv;
    private final AmongPlayer aPlayer;
    private final String finalNumber;
    private String number;

    public Digit(AmongPlayer aPlayer){
        String finalNumber1;
        Random rand = new Random();

        finalNumber1 = Integer.toString(rand.nextInt(10000));
        while(finalNumber1.length() < 4)
            finalNumber1 = Integer.toString(rand.nextInt(10000));
        this.finalNumber = finalNumber1;

        this.number = "";
        this.aPlayer = aPlayer;
        Inventory inventory = Bukkit.createInventory(null, 9*5, "Digit");

        int j = 3;
        for(int i = 1 ; i <= 9 ; i++){
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(i+"");
            item.setItemMeta(itemMeta);
            inventory.setItem(j , item);
            j++;
            if(j == 6)
                j = 12;
            else if(j == 15)
                j = 21;
        }
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(0+"");
        item.setItemMeta(itemMeta);
        inventory.setItem(31 , item);

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        paperMeta.setDisplayName(this.finalNumber);
        paper.setItemMeta(paperMeta);
        inventory.setItem(40, paper);
        this.inv = inventory;

    }

    public AmongPlayer getAmongPlayer(){
        return aPlayer;
    }

    public String getFinalNumber(){
        return finalNumber;
    }

    public Inventory getInv(){
        return this.inv;
    }

    public void addNumber(String number){
        this.number += number;
        System.out.println("On ajoute le chiffre "+number+" et on a "+this.number);
        System.out.println(this.number.equals(this.getFinalNumber()));
        if(this.number.length() == 4){
            if (this.number.equals(this.getFinalNumber())){
                this.doneTask();
            }else
                this.failTask();
        }
    }

    public void failTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage("Vous avez échoué la tache DIGIT !");
    }

    private void doneTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage("Vous avez réussi la tache DIGIT !");
        aPlayer.doneTask(TaskEnum.DIGIT);
    }

    public void open(){
        Bukkit.getPlayer(this.getAmongPlayer().getPlayerID()).openInventory(this.getInv());
    }
}
