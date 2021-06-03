package triplx.core.ranking.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import triplx.core.ranking.commands.SubCommand;
import triplx.core.ranking.commands.SubCommandManager;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static triplx.core.ranking.utils.Color.cc;

public class RevertCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (sender instanceof Player){

            Player player = (Player) sender;
            if (!DataManager.getRank(player.getUniqueId()).hasPermission(Rank.ADMIN)) {
                sender.sendMessage(cc("&cYou do not have permission to do this."));
                return;
            }

            UUID target = UUID.fromString(DataManager.getUUID(args[0]));
            Rank rank = DataManager.getRank(target);

            Rank playerRank = DataManager.getRank(player.getUniqueId());

            if (rank.isStaff()) {
                if (rank.getRankID() >= playerRank.getRankID() && playerRank != Rank.OWNER) {
                    sender.sendMessage(cc("&cYou cannot revert this player's rank."));
                    return;
                }

                if (rank.isStaff() && rank.getRankID() >= playerRank.getRankID() && playerRank != Rank.OWNER) {
                    sender.sendMessage(Color.cc("&cYou cannot revert that player's rank."));
                    return;
                }

                if (rank == Rank.OWNER && !Objects.equals(player.getName(), "SStealthy")) {
                    sender.sendMessage(Color.cc("&cYou cannot revert that player's rank."));
                    return;
                }

                System.out.println(DataManager.getRankHist(target));
                String rankHist = DataManager.getRankHist(target);

                String[] rankIds = rankHist.split(" ");

                List<Rank> ranks = new ArrayList<>();
                for (String str : rankIds) {
                    int i = Integer.parseInt(str);
                    Rank rankB = Rank.getRank(i);
                    ranks.add(rankB);
                }

                Rank newRank = ranks.get(ranks.size() - 1);

                if (newRank == null) {
                    sender.sendMessage(cc("&c&lTRIPLX &cThere was an internal error when attempting to perform this command."));
                    return;
                }
                DataManager.setRank(target, newRank);
                sender.sendMessage(cc("&aYou reverted " + args[0] + "'s rank to " + newRank.toString() + "."));
                //TODO: queue a message to the target
                return;
            }


        }

        if (!(sender instanceof ConsoleCommandSender)) {
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Color.cc(SubCommandManager.helpMessage));
            return;
        }

        UUID target = UUID.fromString(DataManager.getUUID(args[0]));
        Rank rank = DataManager.getRank(target);

        if (rank.isStaff()) {
            sender.sendMessage(cc("&cA player (ADMIN) must do it on the server."));
            return;
        }

        Rank newRank = Rank.getRank(DataManager.getRankHist(target).split(" ")[DataManager.getRankHist(target).length() - 1]);
        DataManager.setRank(target, newRank);
        sender.sendMessage(cc("&aYou reverted " + args[0] + "'s rank to " + newRank.toString() + "."));
    }
}
