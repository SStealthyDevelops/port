package triplx.core.ranking.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import triplx.core.ranking.data.DataManager;
import triplx.core.ranking.rank.Rank;
import triplx.core.ranking.utils.Color;

import java.util.Objects;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent e) {
        DataManager.createPlayer(e.getPlayer());
        Player p = e.getPlayer();

        Rank rank = DataManager.getRank(p);

        if (Objects.equals(rank.getName(), "developer")) {
            p.setPlayerListName(Color.cc("&7[&eDEV&7] &e") + p.getName());
        } else {
            if (Objects.equals(rank.getName(), "youtube")) {
                p.setPlayerListName(Color.cc("&7[&cY&fT&7] &f"));
            }

            p.setPlayerListName(Color.cc(rank.getPrefix() + p.getName()));
        }
    }


}
