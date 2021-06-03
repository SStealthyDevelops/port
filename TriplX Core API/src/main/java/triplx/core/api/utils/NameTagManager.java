package triplx.core.api.utils;

import com.nametagedit.plugin.NametagEdit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import triplx.core.api.chat.Color;

public class NameTagManager {

    @Getter
    @Setter
    private static NameTagManager instance;

    public void setPrefix(final Player player, final String prefix) {
        NametagEdit.getApi().setPrefix(player, Color.cc(prefix));
    }
}
