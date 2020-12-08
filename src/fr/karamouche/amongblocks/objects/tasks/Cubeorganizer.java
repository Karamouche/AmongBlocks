package fr.karamouche.amongblocks.objects.tasks;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Colorable;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Cubeorganizer {

    private final Main main;
    private final Inventory inv;
    private final AmongPlayer aPlayer;
    private int cubeAmount;
    private ItemStack clickedCube;

    public Cubeorganizer(AmongPlayer aPlayer, Main main){
        this.main = main;
        this.aPlayer = aPlayer;
        this.clickedCube = null;

        Inventory inv = Bukkit.createInventory(null, 9*5, "Cube organizer");

        ItemStack limiter = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta limiterMeta = limiter.getItemMeta();

        ItemStack redSpace = main.constructSpacer(14, ChatColor.RED+"Mettre les blocs rouges ici");
        ItemStack greenSpace = main.constructSpacer(13, ChatColor.RED+"Mettre les blocs verts ici");
        ItemStack blueSpace = main.constructSpacer(11, ChatColor.RED+"Mettre les blocs bleus ici");

        limiterMeta.setDisplayName(" ");
        limiter.setItemMeta(limiterMeta);

        ItemStack greenContainer = main.constructWool(DyeColor.GREEN, 1, ChatColor.GREEN+"Boite de laine verte");
        ItemStack redContainer = main.constructWool(DyeColor.RED, 1, ChatColor.RED+"Boite de laine rouge");
        ItemStack blueContainer = main.constructWool(DyeColor.BLUE, 1, ChatColor.BLUE+"Boite de laine bleue");

        inv.setItem(0, redContainer);
        inv.setItem(1, redContainer);
        inv.setItem(2, redContainer);

        inv.setItem(3, greenContainer);
        inv.setItem(4, greenContainer);
        inv.setItem(5, greenContainer);

        inv.setItem(6, blueContainer);
        inv.setItem(7, blueContainer);
        inv.setItem(8, blueContainer);

        inv.setItem(9, redContainer);
        inv.setItem(10, redSpace);
        inv.setItem(11, redContainer);

        inv.setItem(12, greenContainer);
        inv.setItem(13, greenSpace);
        inv.setItem(14, greenContainer);

        inv.setItem(15, blueContainer);
        inv.setItem(16, blueSpace);
        inv.setItem(17, blueContainer);

        inv.setItem(18, redContainer);
        inv.setItem(1+18, redContainer);
        inv.setItem(2+18, redContainer);

        inv.setItem(3+18, greenContainer);
        inv.setItem(4+18, greenContainer);
        inv.setItem(5+18, greenContainer);

        inv.setItem(6+18, blueContainer);
        inv.setItem(7+18, blueContainer);
        inv.setItem(8+18, blueContainer);

        for (int j = 27 ; j <= 35 ;j++){
            inv.setItem(j, limiter);
        }
        //GENERER ICI LES 8 CUBES A RANGER DANS LA BONNE CASE
        ItemStack itemRouge = main.constructWool(DyeColor.RED, 1, " ");
        ItemStack itemVert = main.constructWool(DyeColor.GREEN, 1, " ");
        ItemStack itemBleu = main.constructWool(DyeColor.BLUE, 1, " ");

        List<ItemStack> itemList =  new ArrayList<>();
        itemList.add(itemRouge);
        itemList.add(itemVert);
        itemList.add(itemBleu);

        Random rand = new Random();
        for(int i = 36 ; i <= 44 ; i++){
            int slot = rand.nextInt(9);
            System.out.println(slot+36);
            while(inv.getItem(slot+36) != null)
                slot = rand.nextInt(9);
            inv.setItem(slot+36, itemList.get(rand.nextInt(3)));
        }
        this.cubeAmount = 9;
        this.inv = inv;
    }

    public AmongPlayer getAmongPlayer(){
        return this.aPlayer;
    }

    public Inventory getInv(){
        return this.inv;
    }

    public void failTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage(main.getGame().getTag()+"Vous avez échoué la tache cubeorganizer");
    }

    private void doneTask() {
        AmongPlayer aPlayer = this.getAmongPlayer();
        aPlayer.setActualTask(null);
        Bukkit.getPlayer(aPlayer.getPlayerID()).closeInventory();
        Bukkit.getPlayer(aPlayer.getPlayerID()).sendMessage(main.getGame().getTag()+"Vous avez réussi la tache cubeorganizer !");
        aPlayer.doneTask(TaskEnum.CUBEORGANIZER);
    }

    public void open(){
        Bukkit.getPlayer(this.getAmongPlayer().getPlayerID()).openInventory(this.getInv());
    }

    public void setClickedCube(ItemStack item){
        this.clickedCube = item;
    }

    public void placeCube(){
        this.cubeAmount--;
        if(cubeAmount == 0){
            this.doneTask();
        }
    }

    public ItemStack getClickedCube(){
        return this.clickedCube;
    }

    public void ismoved(int slot) {
        ItemStack clickedCube = this.clickedCube;
        if(clickedCube.getData() instanceof Colorable){
            Colorable colorable = (Colorable) clickedCube.getData();
            DyeColor color = colorable.getColor();
            switch (slot){
                case 10:
                    if(color.equals(DyeColor.RED)){
                        this.setClickedCube(null);
                        placeCube();
                        Bukkit.getPlayer(aPlayer.getPlayerID()).playSound(Bukkit.getPlayer(aPlayer.getPlayerID()).getLocation(),
                                Sound.NOTE_PLING, 10 ,10);
                    }else
                        this.failTask();
                    break;
                case 13:
                    if(color.equals(DyeColor.GREEN)){
                        this.setClickedCube(null);
                        placeCube();
                        Bukkit.getPlayer(aPlayer.getPlayerID()).playSound(Bukkit.getPlayer(aPlayer.getPlayerID()).getLocation(),
                                Sound.NOTE_PLING, 10 ,10);
                    }else
                        this.failTask();
                    break;
                case 16:
                    if(color.equals(DyeColor.BLUE)){
                        this.setClickedCube(null);
                        placeCube();
                        Bukkit.getPlayer(aPlayer.getPlayerID()).playSound(Bukkit.getPlayer(aPlayer.getPlayerID()).getLocation(),
                                Sound.NOTE_PLING, 10 ,10);
                    }else
                        this.failTask();
                    break;
                default:
                    this.failTask();
            }
        }
    }
}
