package core.triplx.punishment.events;

import core.triplx.punishment.constructors.ban.Ban;
import core.triplx.punishment.mongo.BanData;
import core.triplx.punishment.utils.time.Time;
import core.triplx.punishment.utils.time.TimeUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import triplx.core.api.chat.Color;

import java.util.UUID;

public class PlayerJoinQuit implements Listener {

    @EventHandler @Deprecated
    public void onJoinEvent(PlayerJoinEvent e) {
        // dont really need this..
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();

        if (BanData.getInstance().isBanned(uuid)) {

            Ban b = BanData.getInstance().getBan(uuid);


            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, Color.cc(getBanMessage(b)));
            e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
        }
    }

    public static String getBanMessage(Ban b) {
        if (b == null) {
            System.out.println("Ban is null!");
            return "";
        }

        boolean perm = b.isPermanent();
        String kickMessage;
        long timeRemaining = b.getEndTime() - System.currentTimeMillis();
        System.out.println(timeRemaining);
        String reason = b.getReason();
        String type = "";
        int id = b.getId();

        reason = Color.cc(reason);

        switch (b.getType()) {
            case ANTICHEAT:
                type = "&c[SPARTAN]";
                break;
            case GAME_OFFENSE:
                type = "&c[GA-OFF]";
                break;
            case STORE_OFFENSE:
                type = "&c[STORE]";
                break;
            case CHAT:
                type = "&c[CHAT]";
                break;
            case OTHER:
                type = "&c[OTHER]";
        }

        String appendix = perm ? "permanently" : "temporarily";
        kickMessage = Color.cc("&cYou have been " + appendix + " banned from this server!");
        Time time = TimeUtil.toTime(timeRemaining);
        if (!perm) {
            kickMessage += Color.cc("\nTime remaining: &f" + time.makeString());
        }
        kickMessage += Color.cc("\n\n&cReason: &f" + reason + " &c" + type + " &7[ID-" + id + "&7]");
        kickMessage += Color.cc("\n&cAppeal ban: &b&nappeal.triplxmc.net");

        // [Description] [Type][Type-ID] [ID]
        // ex: Cheating/Exploits [SPARTAN][KA-9] [TR-1028102]
        return kickMessage;
    }

}
