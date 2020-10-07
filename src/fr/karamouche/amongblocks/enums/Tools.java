package fr.karamouche.amongblocks.enums;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public enum Tools {
    COLORPICKER(Material.WATCH, ChatColor.LIGHT_PURPLE+"Choisi ta couleur", ChatColor.GRAY+"Clique pour choisir la couleur de ton personnage");

    private final Material mat;
    private final String title;
    private final String lore;

    Tools(Material mat, String title, String lore){
        this.mat = mat;
        this.title = title;
        this.lore = lore;
    }

    public ItemStack toItem(){
        ItemStack item = new ItemStack(this.mat);

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(this.title);
        itemMeta.setLore(Collections.singletonList(this.lore));
        item.setItemMeta(itemMeta);
        return item;
    }
}
