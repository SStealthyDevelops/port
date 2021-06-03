package triplx.core.api.handlers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PluginHandler {

    @Getter
    private ArrayList<Plugin> plugins;

    public PluginHandler() {
        plugins = new ArrayList<>();
        plugins.clear();
    }

    public void reloadPlugin(String name) {
        disablePlugin(name);
        enablePlugin(name);
    }

    public void disablePlugin(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equalsIgnoreCase(name)) {
                Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            }
        }
    }

    public void enablePlugin(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equalsIgnoreCase(name)) {
                Bukkit.getServer().getPluginManager().enablePlugin(plugin);
            }
        }
    }

    public String getVersion(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equalsIgnoreCase(name)) {
                return plugin.getDescription().getVersion();
            }
        }
        return "UNDEFINED";
    }

    public void registerPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    public boolean pluginExists(String name) {
        for (Plugin pl : plugins) {
            if (pl.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
