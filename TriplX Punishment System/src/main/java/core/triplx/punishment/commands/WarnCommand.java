package core.triplx.punishment.commands;

import core.triplx.punishment.constructors.Warning;
import core.triplx.punishment.mongo.WarnData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

import java.util.UUID;


//TODO DOESNT WORK
public class WarnCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("warn")) {

            try {

                // warn <Player> <Category> <Reason>
                // warn SStealthy Chat Cursing

                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!RankingManager.getRank(p).hasPermission(Rank.HELPER)) {
                        sender.sendMessage(Color.cc("&cYou do not have permission to do this!"));
                        return true;
                    }
                }

                if (args.length < 3) {
                    sender.sendMessage(Color.cc("&cUsage: /warn <player> <chat/game/other> <reason>"));
                    sender.sendMessage(Color.cc("&cIt is recommended to use /punish!"));
                    return true;
                }

                String name = args[0];
                String cat = args[1];
                StringBuilder reasonBuilder = new StringBuilder();

                for (int i = 2; i < args.length; i++) {
                    reasonBuilder.append(args[i]).append(" ");
                }

                UUID uuid = UsernameLookup.getUUID(name);
                if (uuid == null) {
                    sender.sendMessage(Color.cc("&cCould not find player " + args[0]));
                    return true;
                }

                String realName = UsernameLookup.getFormattedName(uuid);

                String reason = reasonBuilder.toString();

                if (!(cat.equalsIgnoreCase("chat") || cat.equalsIgnoreCase("game") || cat.equalsIgnoreCase("other"))) {
                    sender.sendMessage(Color.cc("&cUsage: /warn <player> <chat/game/other> <reason>"));
                    return true;
                }

                Warning w = new Warning(uuid, cat.toUpperCase(), reason);
                WarnData.getInstance().warnPlayer(w);
                sender.sendMessage(Color.cc("&cWarned player &f" + realName + " &cfor &7" + reason));
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(Color.cc("&cThere was an error using /warn! Look at logs for more info. "));
                return true;
            }
        }
        return true;
    }


}
