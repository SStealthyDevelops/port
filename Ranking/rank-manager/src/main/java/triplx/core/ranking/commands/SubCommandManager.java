package triplx.core.ranking.commands;

import org.bukkit.command.CommandSender;
import triplx.core.ranking.commands.subcommands.HistoryCommand;
import triplx.core.ranking.commands.subcommands.RevertCommand;
import triplx.core.ranking.commands.subcommands.SetCommand;
import triplx.core.ranking.utils.Color;

import java.util.HashMap;

public class SubCommandManager {

    private static HashMap<String, SubCommand> commands = new HashMap<>();

    public static void init() {
        // commands.add(new Command());
        commands.put("revert", new RevertCommand());
        commands.put("set", new SetCommand());
        commands.put("history", new HistoryCommand());
    }

    public static String helpMessage = Color.cc("&cUsage: /rank <set/revert/history> <player> (rank)");

    public static void runCommand(CommandSender sender, String command, String[] args) {
        SubCommand cmd = commands.get(command.toLowerCase());
        if (cmd == null) {
            sender.sendMessage(helpMessage);
            return;
        }
        cmd.onExecute(sender, args);
    }

    public static SubCommand getCommand(String label) {
        for (String command : commands.keySet()) {
            if (command.equalsIgnoreCase(label)) {
                return commands.get(command);
            }
        }
        return null;
    }

}
