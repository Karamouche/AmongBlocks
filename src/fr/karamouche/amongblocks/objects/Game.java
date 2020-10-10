package fr.karamouche.amongblocks.objects;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.enums.Roles;
import fr.karamouche.amongblocks.enums.Spawn;
import fr.karamouche.amongblocks.enums.Statut;
import fr.karamouche.amongblocks.objects.tasks.TaskEnum;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import skinsrestorer.shared.exception.SkinRequestException;

import java.util.*;

public class Game {
    private Statut statut;
    private final Map<UUID, AmongPlayer> players;
    private final ArrayList<Color> colorTakenList;
    private final String tag;
    private int nbPlayers = 0;
    private final int maxPlayer;
    private final Main main;
    private final int taskGoal;
    private int taskDoneNum;

    /*TODO
    Create an imposter object with:
        -kill countdown
        -isInVent (bool)
        -sabotage
     */

    public Game(Main main){
        this.statut = Statut.LOBBY;
        this.main = main;
        this.players = new HashMap<>();
        this.tag = ChatColor.LIGHT_PURPLE + "Among"+ ChatColor.DARK_PURPLE+"Blocks §6» ";
        this.maxPlayer = 10;
        this.colorTakenList = new ArrayList<>();
        this.taskGoal = nbPlayers*5;
        this.taskDoneNum = 0;
    }

    public void addPlayer(Player player){
        AmongPlayer aPlayer = new AmongPlayer(main, player);
        this.players.put(player.getUniqueId(), aPlayer);
        this.setNbPlayers(this.getNbPlayers() + 1);
    }

    public void removePlayer(Player player) {
        this.players.remove(player.getUniqueId());
        this.setNbPlayers(this.getNbPlayers() - 1);
    }

    public void start(){
        Random rand = new Random();

        //IMPOSTER CHOICE HERE
        /* TODO
        Put 2 imposteurs, but for tests its simpler to put 1
         */
        AmongPlayer imposteur = (AmongPlayer) this.getPlayers().values().toArray()[rand.nextInt(this.getPlayers().values().size())];
        imposteur.setRole(Roles.IMPOSTER);

        for(AmongPlayer aPlayer : this.getPlayers().values()){
            Player player = Bukkit.getPlayer(aPlayer.getPlayerID());
            //CREWMATE IF NOT IMPOSTER
            if(aPlayer.getRole() == null){
                aPlayer.setRole(Roles.CREWMATE);
            }

            //ON MET LA COULEUR
            if(aPlayer.getColor() == null){
                for(Color color : Color.values()){
                    if(!this.getColorTaken().contains(color)){
                        aPlayer.setColor(color);
                    }
                }
            }
            try {
                // setskin for player skin
                main.getSkinAPI().setSkin(player.getName(), aPlayer.getColor().getSkinName());
                // Force skinrefresh for player
                main.getSkinAPI().applySkin(player);
            } catch (SkinRequestException e) {
                e.printStackTrace();
            }

            //TASK PICKER
            final TaskEnum[] TasksListEnum = TaskEnum.values();
            while(aPlayer.getTasks().size() < 5){
                TaskEnum taskEnum = TasksListEnum[rand.nextInt(TasksListEnum.length)];
                if(!aPlayer.getTasks().contains(taskEnum)){
                    aPlayer.getTasks().add(taskEnum);
                }
            }
            player.teleport(Spawn.SPAWN.getLoc());
            player.getInventory().clear();
            aPlayer.annonceRole();
            aPlayer.giveItems();
        }



        System.out.println("On a lancé la partie");
        setStatut(Statut.INGAME);
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public String getTag() {
        return tag;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public Map<UUID, AmongPlayer> getPlayers(){
        return this.players;
    }

    public AmongPlayer getPlayer(UUID id) {
        return this.players.get(id);
    }

    public ArrayList<Color> getColorTaken(){
        return this.colorTakenList;
    }

    public int getTaskGoal() {
        return taskGoal;
    }

    public int getTaskDoneNum() {
        return taskDoneNum;
    }

    public void addTaskDone(){
        this.taskDoneNum++;
    }
}
