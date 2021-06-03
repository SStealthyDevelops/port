package core.triplx.punishment.commands;

import core.triplx.punishment.guis.guis.PunishHome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

import java.util.UUID;

public class PunishCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("punish")) {

            if (!(sender instanceof Player)) {
                sender.sendMessage(Color.cc("&cOnly players can use this command."));
                return true;
            }

            Player p = (Player) sender;

            if (!RankingManager.getRank(p).hasPermission(Rank.HELPER)) {
                p.sendMessage(Color.cc("&cYou do not have permission to use this command."));
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage(Color.cc("&cUsage: /punish <player>"));
                return true;
            }
            UUID target = UsernameLookup.getUUID(args[0]);
            if (target == null) {
                p.sendMessage(Color.cc("&cCould not find player " + args[0] + "."));
                return true;
            }

            new PunishHome(p, target, UsernameLookup.getFormattedName(target));
            return true;
        }
        return true;
    }


}
