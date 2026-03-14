package org.bukkit.command.defaults;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import com.google.common.collect.ImmutableList;

public class WhitelistCommand extends VanillaCommand {
	
    private static final List<String> WHITELIST_SUBCOMMANDS = ImmutableList.of("add", "remove", "on", "off", "list", "reload");

    public WhitelistCommand() {
        super("whitelist");
        this.description = "Manages the list of players allowed to use this server";
        this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
        this.setPermission("bukkit.command.whitelist.reload;bukkit.command.whitelist.enable;bukkit.command.whitelist.disable;bukkit.command.whitelist.list;bukkit.command.whitelist.add;bukkit.command.whitelist.remove");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (badPerm(sender, "reload")) return true;

                Bukkit.reloadWhitelist();
                Bukkit.broadcastMessage("Whitelist reloaded.");
                return true;
            } else if (args[0].equalsIgnoreCase("on")) {
                if (badPerm(sender, "enable")) return true;

                Bukkit.setWhitelist(true);
                Bukkit.broadcastMessage(sender.getName() + " turned on the whitelist.");
                for(Player onlineplayer : Bukkit.getOnlinePlayers()){
                	if((!onlineplayer.isOp()) && (!onlineplayer.isWhitelisted())){
                		onlineplayer.kickPlayer("Whitelist enabled.");
                	}
                }
                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                if (badPerm(sender, "disable")) return true;

                Bukkit.setWhitelist(false);
                Bukkit.broadcastMessage(sender.getName() + " turned off the whitelist.");
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                if (badPerm(sender, "list")) return true;

                StringBuilder result = new StringBuilder();

                for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }

                    result.append(player.getName());
                }

                sender.sendMessage("Whitelisted Players (" + Bukkit.getWhitelistedPlayers().size() + "): " + result.toString());
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (badPerm(sender, "add")) return true;

                Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);

                Command.broadcastCommandMessage(sender, args[1] + " has been added to the whitelist.");
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (badPerm(sender, "remove")) return true;

                Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);

                Command.broadcastCommandMessage(sender, args[1] + " has been removed from the whitelist.");
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Usage:\n" + usageMessage);
        return false;
    }

    private boolean badPerm(CommandSender sender, String perm) {
        if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
            sender.sendMessage(ChatColor.RED + "You don't have the permission.");
            return true;
        }

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], WHITELIST_SUBCOMMANDS, new ArrayList<String>(WHITELIST_SUBCOMMANDS.size()));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                List<String> completions = new ArrayList<String>();
                for (OfflinePlayer player : Bukkit.getOnlinePlayers()) { // Spigot - well maybe sometimes you haven't turned the whitelist on just yet.
                    String name = player.getName();
                    if (StringUtil.startsWithIgnoreCase(name, args[1]) && !player.isWhitelisted()) {
                        completions.add(name);
                    }
                }
                return completions;
            } else if (args[0].equalsIgnoreCase("remove")) {
                List<String> completions = new ArrayList<String>();
                for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
                    String name = player.getName();
                    if (StringUtil.startsWithIgnoreCase(name, args[1])) {
                        completions.add(name);
                    }
                }
                return completions;
            }
        }
        return ImmutableList.of();
    }
}
