package triplx.core.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import triplx.core.api.chat.Color;

public class TScoreboard {

    public TScoreboard(Player player) {
        if (!player.isOnline()) return;
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        Scoreboard board = sm.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "Dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team team = objective.getScoreboard().registerNewTeam("team1");
        team.addEntry(Color.cc("&bTeam 1: &a"));
        team.setPrefix("");
        team.setSuffix("");
        objective.getScore(Color.cc("&bTeam 1: &a")).setScore(0);

        
    }

}
