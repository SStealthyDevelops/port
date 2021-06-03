package triplx.core.api.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import triplx.core.api.TriplXAPI;
import triplx.core.api.handlers.PluginHandler;

public class CommandPreProcess implements Listener {

    private String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/plugins") || e.getMessage().equalsIgnoreCase("/pl")) {
            e.setCancelled(true);

            TextComponent text = new TextComponent(cc("&aTriplX Registered Plugins"));

            for (int i = 0;  i < TriplXAPI.getInstance().getPluginHandler().getPlugins().size(); i++) {
                Plugin pl = TriplXAPI.getInstance().getPluginHandler().getPlugins().get(i);
                if (i > 0) {
                    TextComponent splitter = new TextComponent(cc("&a, "));
                    text.addExtra(splitter);
                }
                boolean enabled = Bukkit.getServer().getPluginManager().isPluginEnabled(pl);
                TextComponent txt = new TextComponent(cc(enabled ? "&a" + pl.getName() : "&c" + pl.getName()));
                txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(cc("&aVersion: " + pl.getDescription().getVersion() + " | &aClick to "
                        + (enabled ? "&c&lDISABLE" : "&a&lENABLE")
                )).create()));

                String toggleSubCommand = enabled ? "disable" : "enable";

                // devapi plugins enable/disable plugin
                txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/devapi plugins " + toggleSubCommand + " " + pl.getName()));
            }

        }
    }

}
