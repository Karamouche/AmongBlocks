package fr.karamouche.amongblocks.gui;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

public class ColorMenu implements GuiBuilder{
    private final Main main;
    public ColorMenu(Main main){
        this.main = main;
    }

    @Override
    public String name() {
        return ChatColor.LIGHT_PURPLE+"Choisi ta couleur";
    }

    @Override
    public int getSize() {
        return 9*2;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        Wool red = new Wool();
        red.setColor(DyeColor.RED);
        inv.setItem(0, red.toItemStack());

        Wool blue = new Wool();
        blue.setColor(DyeColor.BLUE);
        inv.setItem(1, blue.toItemStack());

        Wool green = new Wool();
        blue.setColor(DyeColor.GREEN);
        inv.setItem(2, green.toItemStack());

        Wool pink = new Wool();
        pink.setColor(DyeColor.PINK);
        inv.setItem(3, pink.toItemStack());

        Wool orange = new Wool();
        orange.setColor(DyeColor.ORANGE);
        inv.setItem(4, orange.toItemStack());

        Wool yellow = new Wool();
        yellow.setColor(DyeColor.YELLOW);
        inv.setItem(5, yellow.toItemStack());

        Wool black = new Wool();
        black.setColor(DyeColor.BLACK);
        inv.setItem(6, black.toItemStack());

        Wool gray = new Wool();
        gray.setColor(DyeColor.GRAY);
        inv.setItem(7, gray.toItemStack());

        Wool purple = new Wool();
        purple.setColor(DyeColor.PURPLE);
        inv.setItem(8, purple.toItemStack());

        Wool brown = new Wool();
        brown.setColor(DyeColor.BROWN);
        inv.setItem(9, brown.toItemStack());

        Wool aqua = new Wool();
        aqua.setColor(DyeColor.LIGHT_BLUE);
        inv.setItem(10, aqua.toItemStack());

        Wool lime = new Wool();
        lime.setColor(DyeColor.LIME);
        inv.setItem(11, lime.toItemStack());


    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) throws InterruptedException {
        if(current.getData() instanceof Wool ){
            Wool wool= (Wool) current.getData();
            DyeColor dyeColor = wool.getColor();
            Color color = Color.get(dyeColor);
            Game game = main.getGame();
            if(!game.getColorTaken().contains(color)){
                AmongPlayer aPlayer = game.getPlayer(player.getUniqueId());
                aPlayer.setColor(color);
                player.sendMessage(game.getTag() + "Vous prenez la couleur " + dyeColor.toString().toLowerCase());
                player.closeInventory();
            }else
                player.sendMessage(game.getTag() + "Cette couleur est déjà prise");
        }
    }
}
