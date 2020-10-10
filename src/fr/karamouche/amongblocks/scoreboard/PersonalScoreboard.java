package fr.karamouche.amongblocks.scoreboard;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Statut;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PersonalScoreboard {
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    final Date date = new Date();
    private final String currentDate = (new SimpleDateFormat("dd-MM-yyyy")).format(date).replace("-", "/");
    private final Main main;

    PersonalScoreboard(Player player, Main main){
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar","AmongBlocks");
        reloadData();
        objectiveSign.addReceiver(player);
        this.main = main;
    }

    public void reloadData(){}

	public void setLines(String ip){
		Game game = main.getGame();
        AmongPlayer amongPlayer = game.getPlayer(uuid);
        objectiveSign.setDisplayName(ChatColor.LIGHT_PURPLE + "Among"+ChatColor.DARK_PURPLE+"Blocks");
        objectiveSign.setLine(0, ChatColor.GRAY + currentDate);
        objectiveSign.setLine(1, "§1");
        if(game.getStatut().equals(Statut.LOBBY)){
            objectiveSign.setLine(2, "§e§lCoins");
            objectiveSign.setLine(3, "§7" + "0" /*myPlugin.getApi().getEcoManager().getBalanceCoins(player)*/);
            objectiveSign.setLine(4, "§2");
            objectiveSign.setLine(5, "§e§lVos Stats");
            objectiveSign.setLine(6, "§3Parties jouées: §b");
            objectiveSign.setLine(7, "§3Victoires: §b");
            objectiveSign.setLine(8, "§3");
            objectiveSign.setLine(9, "§c§lDémarrage:");

            final int missingPlayers = game.getMaxPlayer()-game.getNbPlayers();
            if(missingPlayers == 1)
                objectiveSign.setLine(10, ChatColor.GRAY+"En attente de "+ChatColor.YELLOW+missingPlayers+ChatColor.GRAY+" joueur");
            else
                objectiveSign.setLine(10, ChatColor.GRAY+"En attente de "+ChatColor.YELLOW+missingPlayers+ChatColor.GRAY+" joueurs");
            objectiveSign.setLine(11, "§4");
            objectiveSign.setLine(12, "§8» " + ip);
        }else if(game.getStatut().equals(Statut.INGAME)){
            objectiveSign.setLine(2, ChatColor.LIGHT_PURPLE+"Vous êtes :");
            objectiveSign.setLine(3, amongPlayer.getRole().getColor()+amongPlayer.getRole().toString().toLowerCase());
            for(int i = 4 ; i <= 12 ; i++){
                objectiveSign.setLine(i, "");
            }
        }else{

        }
        objectiveSign.updateLines();
    }

    public void onLogout(){
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}