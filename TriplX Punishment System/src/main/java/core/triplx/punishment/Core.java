package core.triplx.punishment;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import core.triplx.punishment.commands.KickCommand;
import core.triplx.punishment.commands.PunishCommand;
import core.triplx.punishment.commands.WarnCommand;
import core.triplx.punishment.commands.ban.TempbanCommand;
import core.triplx.punishment.commands.ban.UnbanCommand;
import core.triplx.punishment.commands.mute.MuteCommand;
import core.triplx.punishment.commands.mute.UnmuteCommand;
import core.triplx.punishment.events.EventManager;
import core.triplx.punishment.guis.GUIUtils;
import core.triplx.punishment.mongo.BanData;
import core.triplx.punishment.mongo.MongoManager;
import core.triplx.punishment.mongo.MuteData;
import core.triplx.punishment.mongo.WarnData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import triplx.core.api.chat.Color;

public class Core extends JavaPlugin implements PluginMessageListener {

    @Getter
    private static Core instance;

    @Override
    public void onEnable() {
        instance = this;
        MongoManager.init();
        BanData.setInstance(new BanData());
        MuteData.setInstance(new MuteData());
        WarnData.setInstance(new WarnData());

        new EventManager(this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("tempban").setExecutor(new TempbanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("warn").setExecutor(new WarnCommand());
        getCommand("punish").setExecutor(new PunishCommand());
        getCommand("kick").setExecutor(new KickCommand());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        try {
            if (!channel.equals("core:punishment")) {
                return;
            }
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("SomeSubChannel")) {
                // Use the code sample in the 'Response' sections below to read
                // the data.
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cError initializing plugin listener."));
        }
    }

    public void bungeeKick(String playerName, String message) {
        if (Bukkit.getOnlinePlayers() == null || Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(playerName);
        out.writeUTF("\n" + Color.cc(message));

        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        assert player != null;
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }


}
