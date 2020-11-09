package fr.karamouche.amongblocks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import fr.karamouche.amongblocks.enums.Roles;
import fr.karamouche.amongblocks.enums.Statut;
import fr.karamouche.amongblocks.enums.Tools;
import fr.karamouche.amongblocks.gui.ColorMenu;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import fr.karamouche.amongblocks.objects.tasks.Cubeorganizer;
import fr.karamouche.amongblocks.objects.tasks.Digit;
import fr.karamouche.amongblocks.objects.tasks.Ordernumbers;
import fr.karamouche.amongblocks.objects.tasks.TaskEnum;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
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
        AmongPlayer aPlayer = game.getPlayer(player.getUniqueId());
        if(game.getStatut().equals(Statut.LOBBY)){
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getItem() != null && event.getItem().isSimilar(Tools.COLORPICKER.toItem())) {
                    main.getGuiManager().open(player, ColorMenu.class);
                }
            }
        }else if(game.getStatut().equals(Statut.INGAME)){
            if(aPlayer.getRole().equals(Roles.CREWMATE)){
                if(event.getClickedBlock() != null && event.getItem() == null) {
                    Location blockClickedLocation = event.getClickedBlock().getLocation();
                    TaskEnum task = null;
                    for (TaskEnum taskIterator : TaskEnum.values()) {
                        if (taskIterator.toLocation().equals(blockClickedLocation)) {
                            task = taskIterator;
                            break;
                        }
                    }
                    if (aPlayer.getTasks().contains(task))
                        aPlayer.playTask(task);
                }if(event.getItem() != null && event.getItem().isSimilar(Tools.TRACKER.toItem())){
                    aPlayer.openTaskTracker();
                }
            }
        }
    }

    @EventHandler
    public void TrackerClickEvent(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            if(event.getInventory().getName().equals("Vos tasks")){
                if(event.getCurrentItem() != null){
                    Material mat = event.getCurrentItem().getType();
                    TaskEnum clickedTask = null;
                    for(TaskEnum value : TaskEnum.values()){
                        if(value.getMat().equals(mat)){
                            clickedTask = value;
                        }
                    }
                    if(clickedTask != null) {
                        player.setCompassTarget(clickedTask.toLocation());
                        player.closeInventory();
                        player.sendMessage(main.getGame().getTag()+"Votre tracker pointe vers : "+clickedTask.toString().toLowerCase());
                    }
                }
            }
        }
    }

    @EventHandler
    public void DigitEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if(aPlayer.getActualTask() instanceof Digit){
            event.setCancelled(true);
            Digit digit = (Digit) aPlayer.getActualTask();
            ItemStack item = event.getCurrentItem();
            if(item != null && item.getType().equals(Material.STAINED_GLASS_PANE)){
                digit.addNumber(item.getItemMeta().getDisplayName());
            }
        }else
            event.setCancelled(true);
    }

    @EventHandler
    public void CloseDigitInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if(aPlayer.getActualTask() != null && aPlayer.getActualTask() instanceof Digit){
            Digit digit = (Digit) aPlayer.getActualTask();
            digit.failTask();
        }
    }

    @EventHandler
    public void OrderNumbersEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if (aPlayer.getActualTask() instanceof Ordernumbers) {
            event.setCancelled(true);
            Ordernumbers ordernumbers = (Ordernumbers) aPlayer.getActualTask();
            ItemStack item = event.getCurrentItem();
            if (item != null) {
                String stage = ordernumbers.getStage() + 1 + "";
                if (stage.equals(item.getItemMeta().getDisplayName().replace("§6", ""))) {
                    ordernumbers.doneNumber();
                } else
                    ordernumbers.failTask();
            } else
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void CloseOrderNumbersInv(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if(aPlayer.getActualTask() != null && aPlayer.getActualTask() instanceof Ordernumbers){
            Ordernumbers ordernumbers = (Ordernumbers) aPlayer.getActualTask();
            ordernumbers.failTask();
        }
    }

    @EventHandler
    public void CubeOrganizerEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if(aPlayer.getActualTask() instanceof Cubeorganizer){
            event.setCancelled(true);
            Cubeorganizer cubeorganizer = (Cubeorganizer) aPlayer.getActualTask();
            ItemStack item = event.getCurrentItem();
            if(item == null){
                event.setCancelled(true);
            }else{
                if(item.getType().equals(Material.STAINED_GLASS_PANE))
                    if(item.getItemMeta().getDisplayName() != " "){
                        if(cubeorganizer.getClickedCube() != null){
                            cubeorganizer.ismoved(event.getSlot());
                        }
                    }else
                        event.setCancelled(true);
                else if(item.getType().equals(Material.WOOL)){
                    if(item.getItemMeta().getDisplayName() != " ")
                        event.setCancelled(true);
                    else {
                        if(cubeorganizer.getClickedCube() == null) {
                            cubeorganizer.setClickedCube(item);
                            event.setCancelled(true);
                            event.getInventory().setItem(event.getSlot(), null);
                        }else{
                            event.setCancelled(true);
                            player.sendMessage(main.getGame().getTag()+"Vous avez déjà un cube dans la main !");
                        }
                    }
                }
            }
        }else
            event.setCancelled(true);
    }

    @EventHandler
    public void CloseCubeOrganizerInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        AmongPlayer aPlayer = main.getGame().getPlayer(player.getUniqueId());
        if(aPlayer.getActualTask() != null && aPlayer.getActualTask() instanceof Cubeorganizer){
            Cubeorganizer cubeorganizer = (Cubeorganizer) aPlayer.getActualTask();
            cubeorganizer.failTask();
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