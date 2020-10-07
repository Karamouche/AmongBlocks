package fr.karamouche.amongblocks.objects;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Color;
import fr.karamouche.amongblocks.enums.Statut;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import skinsrestorer.shared.exception.SkinRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game {
    private Statut statut;
    private final Map<UUID, AmongPlayer> players;
    private final ArrayList<Color> colorTakenList;
    private final String tag;
    private int nbPlayers = 0;
    private final int maxPlayer;
    private final Main main;

    public Game(Main main){
        this.statut = Statut.LOBBY;
        this.main = main;
        this.players = new HashMap<>();
        this.tag = ChatColor.LIGHT_PURPLE + "Among"+ ChatColor.DARK_PURPLE+"Blocks §6» ";
        this.maxPlayer = 10;
        this.colorTakenList = new ArrayList<>();
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
        for(AmongPlayer aPlayer : this.getPlayers().values()){
            Player player = Bukkit.getPlayer(aPlayer.getPlayerID());
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
        }

        Bukkit.getServer().broadcastMessage("On a lancé la partie");
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
}
