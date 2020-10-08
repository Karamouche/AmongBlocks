package fr.karamouche.amongblocks.commands;

import fr.karamouche.amongblocks.Main;
import fr.karamouche.amongblocks.enums.Roles;
import fr.karamouche.amongblocks.enums.Statut;
import fr.karamouche.amongblocks.objects.AmongPlayer;
import fr.karamouche.amongblocks.objects.Game;
import fr.karamouche.amongblocks.objects.tasks.TaskEnum;
import net.minecraft.server.v1_8_R3.AchievementList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SeeTasksCommand implements CommandExecutor {
    Main main;
    public SeeTasksCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            Game game = main.getGame();
            if(game.getStatut().equals(Statut.INGAME)){
                AmongPlayer aPlayer = game.getPlayer(player.getUniqueId());
                if(aPlayer.getRole().equals(Roles.CREWMATE)){
                    player.sendMessage(game.getTag()+"Voici vos tasks : ");
                    player.sendMessage("");
                    for(TaskEnum taskEnum : TaskEnum.values()){
                        player.sendMessage(taskEnum.toString().toLowerCase());
                    }
                    return true;
                }else
                    player.sendMessage(game.getTag()+"Vous êtes imposteur, vous n'avez pas de tasks !");
            }else
                player.sendMessage("§cCubixMC §4» §cErreur: aucune partie n'est en cours.");
        }
        return false;
    }
}
