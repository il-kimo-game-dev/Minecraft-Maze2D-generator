package kimo.com;

import org.bukkit.entity.Player;

public class MinecraftPlayerLogger {
    private String plugin_name;
    private Player player;

    public MinecraftPlayerLogger(String plugin_name, Player p) {
        this.plugin_name = plugin_name;
        this.player = p;
    }

    public void log(String msg) {
        if(player != null) {
            player.sendMessage("[" + plugin_name + "]: " + msg);
        }
        System.out.println("[" + plugin_name + "]: " + msg);
    }
}
