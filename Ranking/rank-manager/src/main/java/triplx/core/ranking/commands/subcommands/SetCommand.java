package triplx.core.ranking.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.ranking.commands.SubCommand;
import triplx.core.ranking.commands.SubCommandManager;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.Objects;
import java.util.UUID;

import static triplx.core.ranking.utils.Color.cc;

public class SetCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {

        Rank playerRank = null;

        if (sender instanceof Player) {
            playerRank = DataManager.getRank((Player) sender);

            if (!playerRank.hasPermission(Rank.ADMIN) && !Objects.equals(sender.getName(), "SStealthy")) {
                sender.sendMessage(Color.cc("&cYou do not have permission to do this."));
                return;
            }
        }

        boolean store = false;
        if (args.length != 2) {
            if (args[2].equals("--store")) {
                store = true;
            } else {
                sender.sendMessage(cc(SubCommandManager.helpMessage));
                return;
            }
        }


        // rank set <player> <rank>
        if (DataManager.getUUID(args[0]) == null) {
            sender.sendMessage(cc("&cCould not find player " + args[0] + "."));
            return;
        }

        UUID target = UUID.fromString(DataManager.getUUID(args[0]));
        Rank rank = Rank.getRank(args[1]);

        if (rank == null) {
            sender.sendMessage(cc("&cCould not find rank " + args[1] + "."));
            return;
        }

        if(rank == Rank.OWNER) {
            if (store) {
                return;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(cc("&cOnly SStealthy can update a player to have this rank."));
                return;
            }

            Player p = (Player) sender;
            if (!p.getName().equals("SStealthy")) {
                sender.sendMessage(cc("&cOnly SStealthy can update a player to have this rank."));
                return;
            }

            DataManager.setRank(target, Rank.OWNER);
            // TODO: USE API TO SEND THAT PLAYER A MESSAGE IMMEDIATELY/WHEN THEY NEXT LOG ON
            sender.sendMessage(cc("&aYou updated " + args[0] + "'s rank to &c&lOWNER&a."));
            return;
        }

        if (rank.isStaff()) {
            if (store) {
                return;
            }
            if (playerRank == null) {
                sender.sendMessage(cc("&cOnly a player can update a staff rank."));
                return;
            }
            if (rank.getRankID() >= playerRank.getRankID() && playerRank != Rank.OWNER) {
                sender.sendMessage(cc("&cYou cannot update a player to that rank."));
                return;
            }

            Rank targetCurrentRank = DataManager.getRank(target);
            if (targetCurrentRank.isStaff() && targetCurrentRank.getRankID() >= playerRank.getRankID() && playerRank != Rank.OWNER) {
                sender.sendMessage(Color.cc("&cYou cannot update that player's rank."));
                return;
            }

            DataManager.setRank(target, rank);
            sender.sendMessage(cc("&aYou updated " + args[0] + "'s rank to " + rank.getName() + "."));
            //TODO: queue a message to the target
            return;
        }

        // not a staff role
        if (store) {
            Rank prev = DataManager.getRank(target);
            if (prev.hasPermission(rank)) {
                return;
            }
        }
        DataManager.setRank(target, rank);
        sender.sendMessage(cc("&aYou updated " + args[0] + "'s rank to " + rank.getName() + "."));
    }
}
