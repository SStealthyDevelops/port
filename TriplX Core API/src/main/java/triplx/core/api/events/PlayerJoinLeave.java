package triplx.core.api.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import triplx.core.api.data.player.PlayerUniData;
import triplx.core.api.handlers.users.User;
import triplx.core.api.handlers.users.UserHandler;
import triplx.core.api.ranking.RankingManager;
import triplx.core.api.scoreboard.TScoreboard;
import triplx.core.api.utils.UsernameLookup;

public class PlayerJoinLeave implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        User user = new User(e.getPlayer());
        user.setRank(RankingManager.getRank(e.getPlayer()));


        UserHandler.getInstance().addUser(user);

        UsernameLookup.createPlayer(e.getPlayer());
        UsernameLookup.uuids.put(e.getPlayer().getName().toLowerCase(), e.getPlayer().getUniqueId());
        UsernameLookup.names.put(e.getPlayer().getName().toLowerCase(), e.getPlayer().getName());

        PlayerUniData.createPlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        User user = UserHandler.getInstance().getUser(e.getPlayer());
        UserHandler.getInstance().removeUser(user);

        UsernameLookup.uuids.remove(e.getPlayer().getName().toLowerCase());
        UsernameLookup.names.remove(e.getPlayer().getName().toLowerCase());

        PlayerUniData.setLastLogin(e.getPlayer().getUniqueId());

    }

}
