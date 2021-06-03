package triplx.core.api;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import triplx.core.api.chat.Color;
import triplx.core.api.commands.DevAPICommand;
import triplx.core.api.commands.UUIDCommand;
import triplx.core.api.data.MongoManager;
import triplx.core.api.data.server.ServerManager;
import triplx.core.api.events.PlayerJoinLeave;
import triplx.core.api.handlers.APIReloadHandler;
import triplx.core.api.handlers.PluginHandler;
import triplx.core.api.handlers.users.UserHandler;
import triplx.core.api.scoreboard.TScoreboard;
import triplx.core.api.utils.NameTagManager;

import java.util.HashMap;

public class TriplXAPI extends JavaPlugin implements PluginMessageListener {

    @Getter
    private static TriplXAPI instance;

    @Getter
    private PluginHandler pluginHandler;

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(Color.cc("&c&l[TRIPLX DEV API] &aEnabling..."));

        MongoManager.init();
        ServerManager.setInstance(new ServerManager());
        ServerManager.getInstance().init();


        instance = this;
        APIReloadHandler.setInstance(new APIReloadHandler());
        UserHandler.setInstance(new UserHandler());
        NameTagManager.setInstance(new NameTagManager());


        getCommand("devapi").setExecutor(new DevAPICommand());
        getCommand("uuid").setExecutor(new UUIDCommand());
        pluginHandler = new PluginHandler();

        getServer().getPluginManager().registerEvents(new PlayerJoinLeave(), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

    }

    @Override
    public void onDisable() {
        ServerManager.getInstance().unregisterServer();
    }

    public boolean sendPlayer(String name, String server) {
        if (Bukkit.getOnlinePlayers() == null || Bukkit.getOnlinePlayers().size() == 0) return false;

        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(name);
            out.writeUTF(server);

            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

            player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
            return true;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not send player " + name + " to server " + server ));
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        // shouldnt be taking incoming messages?
    }
}

