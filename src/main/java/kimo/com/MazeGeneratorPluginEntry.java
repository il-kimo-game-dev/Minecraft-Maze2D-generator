package kimo.com;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MazeGeneratorPluginEntry extends JavaPlugin implements Listener {
    private PlayerCLI cli;

    public MazeGeneratorPluginEntry() {
        super();

        cli = new PlayerCLI(this);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(cli, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getServer().broadcastMessage("[Maze generator]: disabling plugin");
    }
}
