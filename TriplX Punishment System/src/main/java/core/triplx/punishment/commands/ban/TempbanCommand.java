package core.triplx.punishment.commands.ban;

import core.triplx.punishment.Core;
import core.triplx.punishment.constructors.ban.Ban;
import core.triplx.punishment.constructors.ban.BanReason;
import core.triplx.punishment.events.PlayerJoinQuit;
import core.triplx.punishment.mongo.BanData;
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

public class TempbanCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("tempban")) {

            try {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!RankingManager.getRank(p).hasPermission(Rank.MODERATOR)) {
                        p.sendMessage(Color.cc("&cYou do not have permission to use this command!"));
                        return true;
                    }
                }

                // tempban <player> <time> <reason>

                if (args.length < 3) {
                    sender.sendMessage(Color.cc("&cUsage: /tempban <player> <time> <reason>"));
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
                    if (!(s.equalsIgnoreCase("--spartan") || s.equalsIgnoreCase("--game") || s.equalsIgnoreCase("--store") || s.equalsIgnoreCase("--chat") || s.equalsIgnoreCase("--permanent") || s.equalsIgnoreCase("--punish"))) {
                        reasonBuilder.append(args[i]).append(" ");
                    }
                }
                boolean permanent = false;
                boolean gui = false;
                BanReason type = BanReason.OTHER;
                String argStr = Arrays.toString(args);
                if (argStr.toLowerCase().contains("--spartan")) {
                    type = BanReason.ANTICHEAT;
                }
                if (argStr.toLowerCase().contains("--game")) {
                    type = BanReason.GAME_OFFENSE;
                }
                if (argStr.toLowerCase().contains("--chat")) {
                    type = BanReason.CHAT;
                }
                if (argStr.toLowerCase().contains("--store")) {
                    type = BanReason.STORE_OFFENSE;
                }
                if (argStr.toLowerCase().contains("--permanent")) {
                    permanent = true;
                }
                if (argStr.toLowerCase().contains("--punish")) {
                    gui = true;
                }

                if (type == BanReason.STORE_OFFENSE) {
                    if (sender instanceof Player) {
                        if (!RankingManager.getRank((Player)sender).hasPermission(Rank.ADMIN)) {
                            sender.sendMessage(Color.cc("&cYou cannot punish for store offenses."));
                            return true;
                        }
                    }
                }

                String reason = reasonBuilder.toString();
                Time time = TimeUtil.toTime(timeStr);
                long endTime = permanent ? -1 : time.toLong() + System.currentTimeMillis();
                Ban ban = new Ban(uuid, reason, type, endTime);
                System.out.println(time.endLong());
                BanData.getInstance().banPlayer(ban);
                String message = permanent ? "&cBanned player &f" + realName + " &cpermanently." : "&cBanned player &f" + realName + " &cfor &f" + timeStr + "&c.";
                sender.sendMessage(Color.cc(message));
                if (!gui) {
                    sender.sendMessage(Color.cc("&cNote: It is recommended to use /punish instead!"));
                }

                Core.getInstance().bungeeKick(realName, PlayerJoinQuit.getBanMessage(ban));
                // PRE-GENERATED TEXT.
            } catch (Exception e) {
                sender.sendMessage(Color.cc("&cError executing tempban command! View console logs for more info. \nNote: It is recommended to use /punish!"));
                e.printStackTrace();
                return true;
            }
            return true;
        }
        return true;
    }
}
