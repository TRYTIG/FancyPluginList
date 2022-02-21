package com.trytig.fplist.commands;

import com.trytig.fplist.FPList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PluginsCommand implements CommandExecutor, TabExecutor {
    public PluginsCommand(FPList plugin) {
        plugin.getCommand("plugins").setExecutor(this);
        plugin.getCommand("plugins").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GRAY + "Plugins: " + ChatColor.GOLD + pluginManager.getPlugins().length);

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

            sender.spigot().sendMessage(list);

            if (sender instanceof Player)
                sender.sendMessage(ChatColor.GRAY + "You can click on a plugin to view information about it.");
            if (sender instanceof ConsoleCommandSender)
                sender.sendMessage(ChatColor.GRAY + "Run /plugins <name> for more information on a plugin.");

        } else {
            String requestedPluginName = args[0];

            if (pluginManager.getPlugin(requestedPluginName) != null) {
                Plugin plugin = pluginManager.getPlugin(requestedPluginName);

                sender.sendMessage(ChatColor.GOLD + plugin.getDescription().getName() + ChatColor.GRAY + " version "
                        + ChatColor.GOLD + plugin.getDescription().getVersion());

                String pluginEnableStatus = ChatColor.GOLD + "Yes";
                if (!plugin.isEnabled()) {
                    pluginEnableStatus = ChatColor.RED + "Yes";
                }
                sender.sendMessage(ChatColor.GRAY + "Enabled: " + ChatColor.GOLD + pluginEnableStatus);
                sender.sendMessage(ChatColor.GRAY + "Description: " + ChatColor.GOLD + plugin.getDescription().getDescription());

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

                sender.sendMessage(ChatColor.GRAY + authorLabel + ChatColor.GOLD + authors);
            } else {
                // Plugin does not exist
                sender.sendMessage(ChatColor.RED + "This server is not running any plugin by that name!");
                sender.sendMessage(ChatColor.RED + "Use /plugins to get a list of plugins.");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();

        List<String> pluginNames = new ArrayList<>();

        for (Plugin plugin : pluginManager.getPlugins()) {
            pluginNames.add(plugin.getDescription().getName());
        }

        // Sort list
        Collections.sort(pluginNames);

        return pluginNames;
    }
}
