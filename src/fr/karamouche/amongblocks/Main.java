package fr.karamouche.amongblocks;

import fr.karamouche.amongblocks.commands.ForcestartCommand;
import fr.karamouche.amongblocks.commands.SeeTasksCommand;
import fr.karamouche.amongblocks.gui.GuiBuilder;
import fr.karamouche.amongblocks.gui.GuiManager;
import fr.karamouche.amongblocks.gui.ColorMenu;
import fr.karamouche.amongblocks.objects.Game;
import fr.karamouche.amongblocks.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {
    private ScoreboardManager sc;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Game game;

    //SKIN MANAGER (skin restorer)
    private SkinsRestorer skinsRestorer;
    private SkinsRestorerBukkitAPI skinsRestorerBukkitAPI;


    //GUI MENUS
    private GuiManager guiManager;
    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    @Override
    public void onEnable() {
        System.out.println("Le plugin 'AmongBlocks' se lance");
        System.out.println("    by Karamouche");
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
        getCommand("forcestart").setExecutor(new ForcestartCommand(this));
        getCommand("seetasks").setExecutor(new SeeTasksCommand(this));

        skinsRestorer = JavaPlugin.getPlugin(SkinsRestorer.class);
        skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();

        this.game = new Game(this);

        loadScoreboard();
        loadGui();
    }

    public Game getGame(){
        return this.game;
    }

    public SkinsRestorerBukkitAPI getSkinAPI(){
        return this.skinsRestorerBukkitAPI;
    }

    /*SCOREBOARD METHODS*/

    private void loadScoreboard() {
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        sc = new ScoreboardManager(this);
    }

    public ScoreboardManager getScoreboardManager() {
        return sc;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    //GUI MENUS
    public GuiManager getGuiManager() {
        return guiManager;
    }

    private void loadGui(){
        guiManager = new GuiManager(this);
        Bukkit.getPluginManager().registerEvents(guiManager, this);
        registeredMenus = new HashMap<>();
        guiManager.addMenu(new ColorMenu(this));
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    @Override
    public void onDisable() {
        System.out.println("Le plugin 'AmongBlocks' s'éteint");
        for(Player player : Bukkit.getOnlinePlayers()){
            getSkinAPI().removeSkin(player.getName());
        }
    }

    public ItemStack constructWool(DyeColor color, int stackQ, String name){
        Wool wool = new Wool();
        wool.setColor(color);
        ItemStack woolItem = wool.toItemStack();
        woolItem.setAmount(stackQ);
        ItemMeta woolItemMeta = woolItem.getItemMeta();
        woolItemMeta.setDisplayName(name);
        woolItem.setItemMeta(woolItemMeta);
        return woolItem;
    }

    public ItemStack constructSpacer(int color, String name){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;

    }

}
