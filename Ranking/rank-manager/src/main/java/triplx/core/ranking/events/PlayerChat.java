package triplx.core.ranking.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        Rank rank = DataManager.getRank(p);

        String newFormat;
        if (rank == Rank.DEFAULT) {
            newFormat = Color.cc(rank.getPrefix() + p.getName() + "&f: &7") + ChatColor.GRAY + e.getMessage();
        } else {
            if (rank.isStaff()) {
                newFormat = Color.cc(rank.getPrefix() + p.getName() + "&f: " + e.getMessage());
                e.setFormat(newFormat);
                return;
            }
            newFormat = Color.cc(rank.getPrefix() + p.getName() + "&f: ") + ChatColor.RESET + e.getMessage();
        }
        e.setFormat(newFormat);
    }

}
