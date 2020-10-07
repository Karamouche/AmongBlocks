package fr.karamouche.amongblocks.gui;

import fr.karamouche.amongblocks.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GuiManager implements Listener {

    private final Main main;

    public GuiManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        try{
            if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        }catch (NullPointerException e){
            return;
        }

        main.getRegisteredMenus().values().stream()
                .filter(menu -> inv.getName().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    try {
                        menu.onClick(player, inv, current, event.getRawSlot());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    event.setCancelled(true);
                });

    }

    public void addMenu(GuiBuilder m){
        main.getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){

        if(!main.getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = main.getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);

        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(inv);
            }

        }.runTaskLater(main, 1);

    }

}
