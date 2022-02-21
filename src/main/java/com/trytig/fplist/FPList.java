package com.trytig.fplist;

import com.trytig.fplist.commands.PluginsCommand;
import com.trytig.fplist.events.PluginCommandPreProcessEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class FPList extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new PluginsCommand(this);
        new PluginCommandPreProcessEvent(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
