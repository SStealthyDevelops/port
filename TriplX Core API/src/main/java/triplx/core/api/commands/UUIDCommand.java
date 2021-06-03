package triplx.core.api.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

import java.util.UUID;

public class UUIDCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("uuid")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Color.cc("&cOnly a player can use this command. Try mcuuid.net instead."));
                return true;
            }

            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            TextComponent text = new TextComponent(Color.cc("&f" + uuid.toString()));

            TextComponent copy = new TextComponent(Color.cc("&c&l[COPY]"));
            copy.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, uuid.toString()));
            text.addExtra(copy);
            player.spigot().sendMessage(text);
        }
        return true;
    }


}
