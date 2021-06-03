package core.triplx.punishment.events;

import core.triplx.punishment.constructors.mute.Mute;
import core.triplx.punishment.mongo.MuteData;
import core.triplx.punishment.utils.time.TimeUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import triplx.core.api.chat.Color;

public class PlayerChat implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (MuteData.getInstance().isMuted(e.getPlayer().getUniqueId())) { // cache this data
            e.setCancelled(true);
            e.setMessage(null);
            Mute m = MuteData.getInstance().getMute(e.getPlayer().getUniqueId());
            String timeStr = TimeUtil.toTime(m.getEndTime() - System.currentTimeMillis()).makeString();
            String appendix = m.isPermanent() ? "permanently" : "&cfor &f" + timeStr;
            TextComponent text = new TextComponent(Color.cc("&cYou may not speak! You have been muted " + appendix + "&cReason: &f" + m.getReason() + "&c. "));
            TextComponent id = new TextComponent(Color.cc("&7&l[ID]"));

            TextComponent appeal = new TextComponent(Color.cc("&b&l[APPEAL]"));
            appeal.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.cc("&cAppeal at &b&nappeal.triplxmc.net")).create()));

            appeal.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "appeal.triplxmc.net"));

            id.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.cc("&7[ID-" + m.getId() + "] Click to copy.")).create()));
            id.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, m.getId() + ""));



            text.addExtra(id);
            text.addExtra(appeal);
            e.getPlayer().spigot().sendMessage(text);
        }
    }

}
