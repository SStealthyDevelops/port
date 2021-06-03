package triplx.core.ranking.commands.subcommands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.ranking.commands.SubCommand;
import triplx.core.ranking.commands.SubCommandManager;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HistoryCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.cc("&cOnly a player can use this command!"));
            return;
        }

        Rank rank = DataManager.getRank((Player) sender);

        if (!rank.isStaff()) {
            sender.sendMessage(Color.cc("&cYou do not have permission to do this."));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Color.cc(SubCommandManager.helpMessage));
            return;
        }

        // usage- /rank history <player>
//        sender.sendMessage(args);

        UUID target = UUID.fromString(DataManager.getUUID(args[0]));

        String rankHist = DataManager.getRankHist(target);

        String[] rankIds = rankHist.split(" ");

        List<Rank> ranks = new ArrayList<>();
        for (String str : rankIds) {
            int i = Integer.parseInt(str);
            Rank rankB = Rank.getRank(i);
            ranks.add(rankB);
        }

//        String beginning = Color.cc("&a" + args[0] + "'s previous ranks: ");
//        String splitter = Color.cc("\n  &f- ");
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(beginning);
//
//        for (Rank rank1 : ranks) {
//            stringBuilder.append(splitter).append(rank1.getPrefix());
//        }
//
//        stringBuilder.append("\n&aCurrent rank: ").append(DataManager.getRank(target));
//
//        sender.sendMessage(Color.cc(stringBuilder.toString()));

        TextComponent text = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&a" + args[0] + "'s previous ranks: "));

        for (Rank rank1 : ranks) {
            TextComponent newComp = new TextComponent(ChatColor.translateAlternateColorCodes('&', "\n  &f- " + rank1.getPrefix()));
            newComp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "Revert Rank to " + rank1.getPrefix())).create()));
            newComp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rank set " + args[0] + " " + rank1.getName()));
            text.addExtra(newComp);
        }

        TextComponent text2 = new TextComponent(ChatColor.translateAlternateColorCodes('&', "\n&a&lCURRENT RANK: &a" + DataManager.getRank(target).getName()));

        text.addExtra(text2);
        Player player = (Player) sender;
        player.spigot().sendMessage(text);
    }
}
