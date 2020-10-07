package fr.karamouche.amongblocks;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForcestartCommand implements CommandExecutor {
    Main main;
    public ForcestartCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        main.getGame().start();
        return true;
    }
}
