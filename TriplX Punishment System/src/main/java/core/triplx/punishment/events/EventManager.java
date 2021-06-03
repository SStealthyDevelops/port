package core.triplx.punishment.events;

import core.triplx.punishment.Core;
import org.bukkit.plugin.PluginManager;

public class EventManager {

    public EventManager(Core core) {
        PluginManager pm = core.getServer().getPluginManager();
        pm.registerEvents(new PlayerJoinQuit(), core);
        pm.registerEvents(new PlayerChat(), core);
        pm.registerEvents(new InventoryClick(), core);
    }

}
