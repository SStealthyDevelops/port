package triplx.core.api.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.TriplXAPI;
import triplx.core.api.chat.Color;
import triplx.core.api.handlers.APIReloadHandler;
import triplx.core.api.handlers.PluginHandler;
import triplx.core.api.handlers.users.UserHandler;
import triplx.core.api.ranking.Rank;
import triplx.core.api.ranking.RankingManager;

import java.util.Objects;

public class DevAPICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.cc("&cOnly players can use this command."));
            return false;
        }

        Player player = (Player) sender;
        if (!RankingManager.getRank(player).hasPermission(Rank.ADMIN)) {
            player.sendMessage(Color.cc("&cYou do not have permission to this."));
            return false;
        }



        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(Color.cc("&c&l[TRIPLX] &5Reloading Developer API."));
                APIReloadHandler.getInstance().reload(sender);

                return true;
            } else if (args[0].equalsIgnoreCase("flushlocal")) {
                sender.sendMessage(Color.cc("&c&l[TRIPLX] &cFlushing locally stored data."));

                UserHandler.getInstance().flush();

                return true;
            } else return true;

        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("plugins")) {
                if (!Objects.equals(args[1].toLowerCase(), "enable") && !Objects.equals(args[1].toLowerCase(), "disable")) {
                    sender.sendMessage(Color.cc("&cUsage: /devapi plugins <enable/disable> <plugin>"));
                    return false;
                }

                String plugin = args[2];
                PluginHandler handler = TriplXAPI.getInstance().getPluginHandler();
                if (!handler.pluginExists(plugin)) {
                    sender.sendMessage(Color.cc("&cCould not find plugin " + plugin + "."));
                    return false;
                }

                boolean enable = args[1].equalsIgnoreCase("enable");

                if (enable) {
                    handler.enablePlugin(plugin);
                    sender.sendMessage(Color.cc("&7[&cTRIPLX&7] &aYou enabled " + plugin + " v" + handler.getVersion(plugin) + "."));
                } else {
                    handler.disablePlugin(plugin);
                    sender.sendMessage(Color.cc("&7[&cTRIPLX&7] &cYou disabled " + plugin + " v" + handler.getVersion(plugin) + "."));
                }
            } else return false;
        }


        String message = Color.cc("&c&l[TRIPLX] &a&lDEVELOPER API: " +
                "\nEnabled: TRUE" +
                "\nVersion: " + TriplXAPI.getInstance().getDescription().getVersion());
        player.sendMessage(message);

        TextComponent reload = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&5&l[RELOAD]"));
        reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&cReload the TRIPLX developer API.")).create()));
        reload.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/devapi reload"));

        player.spigot().sendMessage(reload);
        return false;
    }
}
