package com.trytig.fplist.events;

import com.trytig.fplist.FPList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginCommandPreProcessEvent implements Listener {
    // I don't really like this method, but it seems to be one of the recommended by md_5
    // https://www.spigotmc.org/threads/overriding-bukkits-plugin-fails-while-overriding-any-other-bukkit-command-succeeds.205900/#post-2132580
    // This is to override the integrated /plugins and /pl that bukkit implements without having to mess with aliases or server configurations
    // This is an almost exact copy of the PluginCommands class but with a different event and won't be called when console sends the command.
    public PluginCommandPreProcessEvent(FPList plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String[] command = event.getMessage().split(" ");

        // Get if command was /plugins or /pl (NOT "/bukkit:", we don't want to override those)
        if (!(command[0].equalsIgnoreCase("/plugins") || command[0].equalsIgnoreCase("/pl")))
            return;

        Player player = event.getPlayer();

        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        if (command.length == 1) {
            player.sendMessage(ChatColor.GRAY + "Plugins: " + ChatColor.GOLD + pluginManager.getPlugins().length);

            TextComponent list = new TextComponent("List: ");
            list.setColor(ChatColor.GRAY);

            int pluginIdx = 0;
            for (Plugin plugin : pluginManager.getPlugins()) {
                pluginIdx++;

                TextComponent pluginName = new TextComponent(plugin.getName());
                pluginName.setColor(ChatColor.GOLD);
                pluginName.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/plugins " + plugin.getName()));

                list.addExtra(pluginName);

                if (pluginManager.getPlugins().length != pluginIdx) {
                    TextComponent pluginNameSeparator = new TextComponent(", ");
                    pluginNameSeparator.setColor(ChatColor.GRAY);

                    list.addExtra(pluginNameSeparator);
                }
            }

            player.spigot().sendMessage(list);

            player.sendMessage(ChatColor.GRAY + "You can click on a plugin to view information about it.");
        } else {
            String requestedPluginName = command[1];

            if (pluginManager.getPlugin(requestedPluginName) != null) {
                Plugin plugin = pluginManager.getPlugin(requestedPluginName);

                player.sendMessage(ChatColor.GOLD + plugin.getDescription().getName() + ChatColor.GRAY + " version "
                        + ChatColor.GOLD + plugin.getDescription().getVersion());

                String pluginEnableStatus = ChatColor.GOLD + "Yes";
                if (!plugin.isEnabled()) {
                    pluginEnableStatus = ChatColor.RED + "Yes";
                }
                player.sendMessage(ChatColor.GRAY + "Enabled: " + ChatColor.GOLD + pluginEnableStatus);
                player.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.GOLD + plugin.getDescription().getDescription());

                StringBuilder authors = new StringBuilder();

                int authorIdx = 0;
                for (String author : plugin.getDescription().getAuthors()) {
                    authorIdx++;

                    authors.append(ChatColor.GOLD).append(author);

                    if (plugin.getDescription().getAuthors().size() != authorIdx) {
                        if (authorIdx == plugin.getDescription().getAuthors().size() - 1)
                            authors.append(ChatColor.GRAY).append(" and ");
                        else
                            authors.append(ChatColor.GRAY).append(", ");
                    }
                }

                String authorLabel = "Author: ";

                if (plugin.getDescription().getAuthors().size() > 1)
                    authorLabel = "Authors: ";

                player.sendMessage(ChatColor.GRAY + authorLabel + ChatColor.GOLD + authors);
            } else {
                // Plugin does not exist
                player.sendMessage(ChatColor.RED + "This server is not running any plugin by that name!");
                player.sendMessage(ChatColor.RED + "Use /plugins to get a list of plugins.");
            }
        }
        event.setCancelled(true);
    }
}
