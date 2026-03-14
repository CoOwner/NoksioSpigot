package org.bukkit.command.defaults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class OpCommand extends VanillaCommand {
	
    public OpCommand() {
        super("op");
        this.description = "Gives the specified player operator status";
        this.usageMessage = "/op <player>";
        this.setPermission("bukkit.command.op.give");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if(!(sender instanceof ConsoleCommandSender)){
        	sender.sendMessage(ChatColor.RED + "This action can only be performed by the console.");
        	return false;
        }
        if (args.length != 1 || args[0].length() == 0)  {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        player.setOp(true);
        
        if (player instanceof Player) {
        	((Player) player).sendMessage(ChatColor.GREEN + "You have been promoted to operator!");
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

            Player senderPlayer = (Player) sender;

            List<String> matchedPlayers = new ArrayList<String>();
            for (Player player : sender.getServer().getOnlinePlayers()) {
                String name = player.getName();
                if (!senderPlayer.canSee(player) || player.isOp()) {
                    continue;
                }
                if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                    matchedPlayers.add(name);
                }
            }

            Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
            return matchedPlayers;
        }
        return ImmutableList.of();
    }
}
