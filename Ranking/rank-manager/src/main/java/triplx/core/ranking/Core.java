package triplx.core.ranking;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import triplx.core.ranking.commands.RankCommand;
import triplx.core.ranking.commands.SubCommandManager;
import triplx.core.ranking.data.MongoManager;
import triplx.core.ranking.discord.DiscordBot;
import triplx.core.ranking.events.PlayerChat;
import triplx.core.ranking.events.PlayerJoin;
import triplx.core.ranking.utils.Color;

public class Core extends JavaPlugin {

    @Getter
    private static Core instance;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        instance = this;

        getCommand("rank").setExecutor(new RankCommand());
        SubCommandManager.init();

        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(), this);

        Bukkit.getConsoleSender().sendMessage(Color.cc("&c&l[TRIPLX CORE] &aRanking enabled!"));

        if (getConfig().getString("discord-token") == null) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not connect to discord bot- Discord token is null."));
        } else {
            DiscordBot.setInstance(new DiscordBot());
            DiscordBot.getInstance().init();
        }

        MongoManager.init();


    }

    @Override
    public void onDisable() {

    }



}
