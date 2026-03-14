package us.noks.rinny.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class LantenceCommand extends Command {
	
    public LantenceCommand(String name) {
        super(name);
        this.description = "Get the ping between you and the server";
        this.usageMessage = "/ping <player>";
        this.setAliases(Arrays.asList("ms"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (args.length >= 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }
        
        Player p = (Player) sender;
        
        if(args.length == 0) {
        	sender.sendMessage(ChatColor.GOLD + "Your ping: " + ChatColor.YELLOW + p.getPing() + " ms. " + ChatColor.GOLD + "(TPS: " + format(Bukkit.spigot().getTPS()[0]) + ChatColor.GOLD + ")");
        	
        } else if(args.length == 1) {
        	Player t = Bukkit.getPlayer(args[0]);
        	
        	if(t == null){
        		sender.sendMessage(ChatColor.RED + "This player is not online!");
        		return false;
        	}
        	if(t == p){
        		sender.sendMessage(ChatColor.GOLD + "Your ping: " + ChatColor.YELLOW + p.getPing() + " ms. " + ChatColor.GOLD + "(TPS: " + format(Bukkit.spigot().getTPS()[0]) + ChatColor.GOLD + ")");
        		return true;
        	}
        	sender.sendMessage(ChatColor.YELLOW + t.getDisguisedName() + ChatColor.GOLD + "'s ping: " + ChatColor.YELLOW + t.getPing() + " ms. " + ChatColor.GOLD + "(TPS: " + format(Bukkit.spigot().getTPS()[0]) + ChatColor.GOLD + ")");
        	sender.sendMessage(ChatColor.GOLD + "Ping difference: " + ChatColor.YELLOW + (Math.max(p.getPing(), t.getPing()) - Math.min(p.getPing(), t.getPing()) + " ms."));
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                return ImmutableList.of();
            }

            String lastWord = args[0];
            if (lastWord.length() == 0) {
                return ImmutableList.of();
            }

            ArrayList<String> matchedPlayers = new ArrayList<String>();
            for (Player player : sender.getServer().getOnlinePlayers()) {
                String name = player.getName();
                if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                    matchedPlayers.add(name);
                }
            }

            Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
            return matchedPlayers;
        }
        return ImmutableList.of();
    }
    
	private static String format(double tps){
		return (( tps > 18.0 ) ? ChatColor.GREEN : ( tps > 16.0 ) ? ChatColor.YELLOW : ChatColor.RED ).toString() + ( ( tps > 20.0 ) ? ChatColor.DARK_GREEN + "*" : "" ) + Math.min( Math.round( tps * 100.0 ) / 100.0, 20.0 );
	}
    // Spigot End
}