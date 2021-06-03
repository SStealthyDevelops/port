package core.triplx.punishment.commands.mute;

import core.triplx.punishment.constructors.mute.Mute;
import core.triplx.punishment.mongo.MuteData;
import core.triplx.punishment.utils.time.Time;
import core.triplx.punishment.utils.time.TimeUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.utils.UsernameLookup;

import java.util.Arrays;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("mute")) {
            try {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!RankingManager.getRank(p).hasPermission(Rank.HELPER)) {
                        sender.sendMessage(Color.cc("&cYou do not have permission to use this command."));
                        return true;
                    }
                }

                // mute <player> <time> <reason>


                if (args.length < 3) {
                    sender.sendMessage(Color.cc("&cUsage: /mute <player> <time> <reason>"));
                    return true;
                }

                String playerName = args[0];
                UUID uuid = UsernameLookup.getUUID(playerName);

                if (RankingManager.getRank(uuid).hasPermission(Rank.HELPER)) { //prevent mods from banning admins or something stupid lol
                    if (sender instanceof Player) {
                        if (!RankingManager.getRank((Player)sender).hasPermission(Rank.ADMIN)) {
                            sender.sendMessage(Color.cc("&cYou cannot punish that player."));
                            return true;
                        }
                        if (!RankingManager.getRank((Player)sender).hasPermission(RankingManager.getRank(uuid))) { // make sure an admin doesnt ban owner or manager or something stupid lol
                            sender.sendMessage(Color.cc("&cYou cannot punish that player."));
                            return true;
                        }
                    }
                }

                String realName = UsernameLookup.getFormattedName(uuid);

                String timeStr = args[1];
                StringBuilder reasonBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    String s = args[i];
                    if (!(s.equalsIgnoreCase("--permanent") || s.equalsIgnoreCase("--punish"))) {
                        reasonBuilder.append(args[i]).append(" ");
                    }
                }
                boolean permanent = false;
                boolean punish = false;
                if (Arrays.toString(args).toLowerCase().contains("--permanent")) {
                    permanent = true;
                }
                if (Arrays.toString(args).toLowerCase().contains("--punish")) {
                    punish = true;
                }

                String reason = reasonBuilder.toString();
                Time time = TimeUtil.toTime(timeStr);
                long endTime = permanent ? -1 : time.toLong() + System.currentTimeMillis();

                Mute mute = new Mute(uuid, reason, endTime);
                MuteData.getInstance().mutePlayer(mute);
                String message = permanent ? "&cMuted player &f" + realName + " &cpermanently." : "&cMuted player &f" + realName + " &cfor &f" + timeStr + "&c.";
                sender.sendMessage(Color.cc(message));
                if (!punish) {
                    sender.sendMessage(Color.cc("&cNote: It is recommended to use /punish instead!"));
                }
            } catch (Exception e) {
                sender.sendMessage(Color.cc("&cError executing mute command! View console logs for more info. \nNote: It is recommended to use /punish!"));
                e.printStackTrace();
                return true;
            }




        }
        return true;
    }


}
