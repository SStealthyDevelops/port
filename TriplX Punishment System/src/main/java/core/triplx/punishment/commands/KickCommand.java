package core.triplx.punishment.commands;

import core.triplx.punishment.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

import java.util.UUID;

public class KickCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!RankingManager.getRank(p).hasPermission(Rank.HELPER)) {
                p.sendMessage(Color.cc("&cYou do not have permission to use this command."));
                return true;
            }
        }

        if (args.length < 2) {
            sender.sendMessage(Color.cc("&cUsage: /kick <player> <reason>"));
            return true;
        }

        String playerName = args[0];
        UUID uuid = UsernameLookup.getUUID(playerName);

        if (RankingManager.getRank(uuid).hasPermission(Rank.HELPER)) { //prevent mods from banning admins or something stupid lol
            if (sender instanceof Player) {
                if (!RankingManager.getRank((Player)sender).hasPermission(Rank.ADMIN)) {
                    sender.sendMessage(Color.cc("&cYou cannot kick that player."));
                    return true;
                }
                if (!RankingManager.getRank((Player)sender).hasPermission(RankingManager.getRank(uuid))) { // make sure an admin doesnt ban owner or manager or something stupid lol
                    sender.sendMessage(Color.cc("&cYou cannot kick that player."));
                    return true;
                }
            }
        }

        String realName = UsernameLookup.getFormattedName(uuid);
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            String s = args[i];
            if (!(s.equalsIgnoreCase("--spartan") || s.equalsIgnoreCase("--game")  || s.equalsIgnoreCase("--chat") || s.equalsIgnoreCase("--permanent") || s.equalsIgnoreCase("--punish"))) {
                reasonBuilder.append(args[i]).append(" ");
            }
        }

        String reason = reasonBuilder.toString();

        String message = "";

        message += "&cYou have been kicked from this server!";
        message += "\n&cReason: &f" + reason + "";
        sender.sendMessage(Color.cc("&cKicked " + playerName + " for " + reason + "."));
        Core.getInstance().bungeeKick(realName, message);
        return true;
    }


}
