package org.spigotmc;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TicksPerSecondCommand extends Command {

    public TicksPerSecondCommand(String name) {
        super( name );
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission( "bukkit.command.tps" );
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        // PaperSpigot start - Further improve tick handling
        double[] tps = Bukkit.spigot().getTPS();
        String[] tpsAvg = new String[tps.length];
        
        for ( int i = 0; i < tps.length; i++) {
            tpsAvg[i] = format(tps[i]);
        }

        sender.sendMessage(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: " + StringUtils.join(tpsAvg, ChatColor.GOLD + ", "));
        showTpsMeter(sender, Bukkit.spigot().getTPS()[0]);
        // PaperSpigot end

        return true;
    }
    
	private static void showTpsMeter(CommandSender sender, double tps) {
		int bars = Math.min((int)Math.round(tps), 20);
		StringBuilder tpsMeter = new StringBuilder(60);
		for (int i = 0; i < 20; i++) {
			tpsMeter.append(bars > i ? ChatColor.GREEN.toString() : ChatColor.RED.toString()).append('|');
		}
		sender.sendMessage(ChatColor.GOLD + "Current Server Performance: " + format(tps) + ChatColor.GOLD + '/' + 20.0D + " [" + tpsMeter + ChatColor.GOLD + ']');
	}

    private static String format(double tps) {
        return (( tps > 18.0 ) ? ChatColor.GREEN : (tps > 16.0) ? ChatColor.YELLOW : ChatColor.RED).toString() + ((tps > 20.0) ? ChatColor.DARK_GREEN + "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 20.0);
    }
}
