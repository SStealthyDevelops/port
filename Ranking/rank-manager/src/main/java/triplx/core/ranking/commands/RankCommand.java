package triplx.core.ranking.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.Objects;

public class RankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Rank rank = DataManager.getRank(player);
            if (!rank.hasPermission(Rank.HELPER) && !Objects.equals(player.getName(), "SStealthy")) {
                player.sendMessage(Color.cc("&cYou do not have permission to do this."));
                return false;
            }
            // has permission
        }

        if (args.length == 0) {
            sender.sendMessage(SubCommandManager.helpMessage);
            return true;
        }


        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                str.append(args[i]).append(" ");
            }
        }

        SubCommandManager.runCommand(sender, args[0], str.toString().split(" "));
        return true;
    }
}
