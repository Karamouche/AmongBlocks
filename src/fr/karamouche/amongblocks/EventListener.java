package fr.karamouche.amongblocks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import fr.karamouche.amongblocks.enums.Statut;
import fr.karamouche.amongblocks.enums.Tools;
import fr.karamouche.amongblocks.gui.ColorMenu;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class EventListener implements Listener {

    private final Main main;
    public EventListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Game game = main.getGame();
        Player player = event.getPlayer();
        main.getScoreboardManager().onLogin(player);
        if(game.getStatut().equals(Statut.LOBBY)){
            game.addPlayer(player);
            event.setJoinMessage(game.getTag()+ ChatColor.YELLOW+event.getPlayer().getName()+ChatColor.AQUA +" a rejoint la partie "+"§8(§a"+game.getNbPlayers()+"§8/§a"+game.getMaxPlayer()+"§8)");
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.setHealth(20);
            player.setLevel(0);
            player.setExp(0);
            player.getInventory().clear();

            AmongPlayer aPlayer = game.getPlayer(player.getUniqueId());
            aPlayer.giveLobbyItems();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Game game = main.getGame();
        Player player = event.getPlayer();
        main.getSkinAPI().removeSkin(player.getName());
        main.getScoreboardManager().onLogout(player);
        if(game.getStatut().equals(Statut.LOBBY)){
            game.removePlayer(player);
            event.setQuitMessage(game.getTag()+ChatColor.YELLOW+event.getPlayer().getName()+ChatColor.GRAY+" a quitté la partie "+"§8(§a"+(game.getNbPlayers())+"§8/§a"+game.getMaxPlayer()+"§8)");

        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!event.getPlayer().isOp() || !main.getGame().getStatut().equals(Statut.LOBBY)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Game game = main.getGame();
        if(game.getStatut().equals(Statut.LOBBY)){
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getItem() != null && event.getItem().isSimilar(Tools.COLORPICKER.toItem())) {
                    main.getGuiManager().open(player, ColorMenu.class);
                }
            }
        }
    }

    @EventHandler
    public void onRoll(PlayerItemHeldEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if(item != null && !item.getType().equals(Material.AIR) && main.getGame().getStatut().equals(Statut.INGAME)){
            //PROTOCOL
            PacketContainer hideItem = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);

            hideItem.getIntegers().write(0, event.getPlayer().getEntityId());
            hideItem.getItemSlots().write(0, EnumWrappers.ItemSlot.MAINHAND);
            hideItem.getItemModifier().write(0, new ItemStack(Material.AIR));
            new BukkitRunnable(){

                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if(!player.equals(event.getPlayer())) {
                            try {
                                ProtocolLibrary.getProtocolManager().sendServerPacket(player, hideItem);
                                System.out.println("On envoie bien le packet de " + event.getPlayer().getName() + " à " + player.getName());
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }.runTaskLater(main, 1);

        }
    }

}
