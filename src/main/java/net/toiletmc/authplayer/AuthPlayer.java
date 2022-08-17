package net.toiletmc.authplayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class AuthPlayer extends JavaPlugin implements Listener {

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            UUID uuid = player.getUniqueId();
            int type = uuid.version();
            if (type == 4) {
                Bukkit.dispatchCommand(getServer().getConsoleSender(),"zb " + player.getName());
                getLogger().info(player.getName() + " 是正版用户，已授权正版称号。");
            }
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("监听器已注册。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
