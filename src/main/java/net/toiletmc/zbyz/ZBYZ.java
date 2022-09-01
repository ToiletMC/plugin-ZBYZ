package net.toiletmc.zbyz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public final class ZBYZ extends JavaPlugin implements Listener {

    boolean isDebug = false;
    List<String> commands;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("zbyz.admin")) {
            if (args[0].equalsIgnoreCase("reload")) {
                saveDefaultConfig();
                reloadConfig();
                isDebug = getConfig().getBoolean("debug",false);
                commands = getConfig().getStringList("commands");
                sender.sendMessage("插件已重载，" + commands.size() + "条命令已加载！");
                if (isDebug) {
                    getLogger().info(commands.toString());
                }
            } else {
                sender.sendMessage(ChatColor.RED + "未知的指令");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "未知的指令");
        }

        return true;
    }

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            UUID uuid = player.getUniqueId();
            int type = uuid.version();
            if (type == 4) {
                for (String command : commands) {
                    Bukkit.dispatchCommand(getServer().getConsoleSender(), command.replace("{player}", player.getName()));
                }
                getLogger().info(player.getName() + " 是正版用户，已执行命令。");
                if (isDebug) {
                    getLogger().info(player.getName());
                    getLogger().info(player.getUniqueId().toString());
                }
            }
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        isDebug = getConfig().getBoolean("debug", false);
        commands = getConfig().getStringList("commands");
        getLogger().info("插件正在运行，" + commands.size() + "条命令已加载！");
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("监听器已注册。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("拜拜~");
    }
}
