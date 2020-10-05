package fr.karamouche.amongblocks;

import fr.karamouche.amongblocks.objects.Game;
import fr.karamouche.amongblocks.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import skinsrestorer.bukkit.SkinsRestorer;
import skinsrestorer.bukkit.SkinsRestorerBukkitAPI;
import skinsrestorer.shared.exception.SkinRequestException;

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

    @Override
    public void onEnable() {
        System.out.println("Le plugin 'AmongBlocks' se lance");
        System.out.println("    by Karamouche");
        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        skinsRestorer = JavaPlugin.getPlugin(SkinsRestorer.class);
        skinsRestorerBukkitAPI = skinsRestorer.getSkinsRestorerBukkitAPI();

        this.game = new Game(this);

        loadScoreboard();
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

    @Override
    public void onDisable() {
        System.out.println("Le plugin 'AmongBlocks' s'éteint");
    }

}
