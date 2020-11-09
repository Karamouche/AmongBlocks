package fr.karamouche.amongblocks.gui;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import net.md_5.bungee.api.ChatColor;
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
        ItemStack red = main.constructWool(DyeColor.RED, 1, ChatColor.RED+"Rouge");
        inv.setItem(0, red);

        ItemStack blue = main.constructWool(DyeColor.BLUE, 1, ChatColor.BLUE+"Bleu");
        inv.setItem(1, blue);

        ItemStack green = main.constructWool(DyeColor.GREEN, 1, ChatColor.DARK_GREEN+"Vert");
        inv.setItem(2, green);

        ItemStack pink = main.constructWool(DyeColor.PINK, 1, ChatColor.LIGHT_PURPLE+"Rose");
        inv.setItem(3, pink);

        ItemStack orange = main.constructWool(DyeColor.ORANGE, 1, ChatColor.GOLD+"Orange");
        inv.setItem(4, orange);

        ItemStack yellow = main.constructWool(DyeColor.YELLOW, 1, ChatColor.YELLOW+"Jaune");
        inv.setItem(5, yellow);

        ItemStack black = main.constructWool(DyeColor.BLACK, 1, ChatColor.BLACK+"Noir");
        inv.setItem(6, black);

        ItemStack gray = main.constructWool(DyeColor.GRAY, 1, ChatColor.GRAY+"Gris");
        inv.setItem(7, gray);

        ItemStack purple = main.constructWool(DyeColor.PURPLE, 1, ChatColor.DARK_PURPLE+"Violet");
        inv.setItem(8, purple);

        ItemStack brown = main.constructWool(DyeColor.BROWN, 1, ChatColor.GOLD+"Marron");
        inv.setItem(9, brown);

        ItemStack aqua = main.constructWool(DyeColor.LIGHT_BLUE, 1, ChatColor.AQUA+"Aqua");
        inv.setItem(10, aqua);

        ItemStack lime = main.constructWool(DyeColor.LIME, 1, ChatColor.GREEN+"Vert");
        inv.setItem(11, lime);


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
                player.sendMessage(game.getTag() + "Vous prenez la couleur " + current.getItemMeta().getDisplayName().toLowerCase());
                player.closeInventory();
            }else
                player.sendMessage(game.getTag() + "Cette couleur est déjà prise");
        }
    }
}
