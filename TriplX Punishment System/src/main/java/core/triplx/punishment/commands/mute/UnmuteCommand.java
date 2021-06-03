package core.triplx.punishment.commands.mute;

import core.triplx.punishment.mongo.MuteData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

import java.util.UUID;

public class UnmuteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("unmute")) {

            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!RankingManager.getRank(p).hasPermission(Rank.MODERATOR)) {
                    p.sendMessage(Color.cc("&cYou do not have permission to use this command!"));
                    return true;
                }
            }

            if (args.length != 1) {
                sender.sendMessage(Color.cc("&cUsage: /unmute <player>"));
                return true;
            }

            UUID uuid = UsernameLookup.getUUID(args[0]);
            if (uuid == null) {
                sender.sendMessage(Color.cc("&cCould not find user " + args[0]));
                return true;
            }

            MuteData.getInstance().pardonPlayer(uuid);
            sender.sendMessage(Color.cc("&cUnmuted player &b" + UsernameLookup.getFormattedName(uuid) + "&c."));

        }
        return true;
    }


}
