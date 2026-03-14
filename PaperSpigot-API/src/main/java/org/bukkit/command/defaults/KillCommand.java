package org.bukkit.command.defaults;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import com.google.common.collect.ImmutableList;

import net.md_5.bungee.api.ChatColor;

public class KillCommand extends VanillaCommand {
	
    public KillCommand() {
        super("kill");
        this.description = "Kill yourself or kill a player.";
        this.usageMessage = "/kill <player>";
        this.setPermission("bukkit.command.kill");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        if (sender instanceof Player) {
        	Player player = (Player) sender;
        	
        	if(args.length > 1) {
        		sender.sendMessage(ChatColor.RED+"Usage: /kill <player>");
        		return false;
        	}
        	if(args.length == 1) {
        		Player target = Bukkit.getPlayer(args[0]);
        		
        		if(target == null) {
        			player.sendMessage(ChatColor.RED+"Player not found!");
        			return false;
        		}
        		EntityDamageEvent ede = new EntityDamageEvent(target, EntityDamageEvent.DamageCause.SUICIDE, 1000);
                Bukkit.getPluginManager().callEvent(ede);
                if (ede.isCancelled()) return true;

                ede.getEntity().setLastDamageCause(ede);
                target.setHealth(0);
                sender.sendMessage("Ouch. That look like it hurt.");
        		return true;
        	}
            EntityDamageEvent ede = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.SUICIDE, 1000);
            Bukkit.getPluginManager().callEvent(ede);
            if (ede.isCancelled()) return true;

            ede.getEntity().setLastDamageCause(ede);
            player.setHealth(0);
            sender.sendMessage("Ouch. That look like it hurt.");
        } else {
            sender.sendMessage("You can only perform this command as a player");
        }

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        return ImmutableList.of();
    }
}
