package org.bukkit.command.defaults;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.google.common.collect.ImmutableList;

public class FlyCommand extends BukkitCommand {

	public FlyCommand(String name) {
		super(name);
		this.description = "Allow you or another player to fly";
		this.usageMessage = "/fly <player>";
		this.setPermission("bukkit.command.fly");
	}

	@Override
	public boolean execute(CommandSender sender, String currentAlias, String[] args) {
		if (!testPermission(sender)) return true;
		
		if (args.length > 1) {
			sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
			return false;
		}

		String playerArg = sender.getName();

		if (args.length == 1) {
			playerArg = args[0];
		}

		Player player = Bukkit.getPlayerExact(playerArg);

		if (player != null) {
			if (player == sender) {
				player.setAllowFlight(!player.getAllowFlight());
				player.setFlying(player.getAllowFlight());
				sender.sendMessage(ChatColor.GOLD + "You are now " + (player.isFlying() ? ChatColor.GREEN + "allowed" : ChatColor.RED + "disallowed") + ChatColor.GOLD + " to fly.");
			} else {
				player.setAllowFlight(!player.getAllowFlight());
				player.setFlying(player.getAllowFlight());
				player.sendMessage(ChatColor.GOLD + "You are now " + (player.isFlying() ? ChatColor.GREEN + "allowed" : ChatColor.RED + "disallowed") + ChatColor.GOLD + " to fly.");
				sender.sendMessage(ChatColor.GOLD + "You have " + (player.isFlying() ? ChatColor.GREEN + "allow" : ChatColor.RED + "disallow") + ChatColor.YELLOW + player.getDisguisedName() + ChatColor.GOLD + " to fly.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Unknown " + playerArg + " player!");
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		Validate.notNull(sender, "Sender cannot be null");
		Validate.notNull(args, "Arguments cannot be null");
		Validate.notNull(alias, "Alias cannot be null");

		if (args.length == 1) {
			return super.tabComplete(sender, alias, args);
		}
		return ImmutableList.of();
	}
}