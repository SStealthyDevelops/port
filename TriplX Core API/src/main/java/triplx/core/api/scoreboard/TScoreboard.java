package triplx.core.api.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Getter
public class TScoreboard {

    /*
    public static Map<UUID, ScoreboardTask> tasks = new HashMap<>();

    @Getter
    private final Map<Integer, List<String>> lines;

    @Getter
    private final List<String> title;

    @Getter
    @Setter
    private int maxCycles;

    public TScoreboard(List<String> title, int maxCycles) {
        this.lines = new HashMap<>();
        this.title = title;
        this.maxCycles = maxCycles;
    }

    public TScoreboard(List<String> title, Map<Integer, List<String>> lines) {
        this.lines = lines;
        this.title = title;
        this.maxCycles = lines.get(0).size();
    }

    public TScoreboard(List<String> title, Map<Integer, List<String>> lines, int maxLines) {
        this.lines = lines;
        this.title = title;
        this.maxCycles = maxLines;
    }

    public void addLine(List<String> line) {
        lines.put(lines.size() + 1, line);
    }

    public void send(Player player) {
        ScoreboardTask task =  new ScoreboardTask(player, this);
        tasks.put(player.getUniqueId(), task);
        Bukkit.getScheduler().runTaskTimer(TriplXAPI.getInstance(), task, 4L, 12L);
    }
    */

    private List<String> title;
    private Map<Integer, List<String>> lines;
    private Scoreboard board;

    public TScoreboard(List<String> title, Map<Integer, List<String>> lines) {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("TriplX", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        this.title = title;
        this.lines = lines;

        for (Map.Entry<Integer, List<String>> entry : lines.entrySet()) {
            if (entry.getValue().size() == 1) {
                objective.getScore(entry.getValue().get(0));
            }
        }



    }


    public void setScoreboard(Player player) {
        player.setScoreboard(board);
    }

}
