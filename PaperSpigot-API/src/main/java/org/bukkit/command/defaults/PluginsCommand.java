package org.bukkit.command.defaults;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginsCommand extends BukkitCommand {
    public PluginsCommand(String name) {
        super(name);
        this.description = "Get all plugins on the server.";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;
        
        sender.sendMessage("Plugins " + getPluginList());
        return true;
    }

    private String getPluginList() {
    	// RinnySpigot start
    	TreeMap<String, ChatColor> plugins = new TreeMap<String, ChatColor>(String.CASE_INSENSITIVE_ORDER);
    	
    	for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
    		plugins.put(plugin.getDescription().getName(), plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
    	}
    	
    	StringBuilder pluginList = new StringBuilder();
    	for (Map.Entry<String, ChatColor> entry : plugins.entrySet()) {
    		if (pluginList.length() > 0) {
    			pluginList.append(ChatColor.WHITE);
    			pluginList.append(", ");
    		}
    		pluginList.append(entry.getValue());
    		pluginList.append(entry.getKey());
    	}
    	 
    	return "(" + plugins.size() + "): " + pluginList.toString();
    	// RinnySpigot end
    }

    // Spigot Start
    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return java.util.Collections.emptyList();
    }
    // Spigot End
}
