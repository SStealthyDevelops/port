package triplx.core.api.handlers.users;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import triplx.core.api.ranking.Rank;

import java.util.UUID;

public class User {

    @Getter
    private Player player;
    @Getter
    private final UUID uuid;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    @Getter
    @Setter
    private Rank rank;


}
