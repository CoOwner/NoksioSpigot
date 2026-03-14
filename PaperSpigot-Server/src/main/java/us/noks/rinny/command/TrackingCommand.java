package us.noks.rinny.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotWorldConfig;

import net.minecraft.server.EntityTracker;
import net.minecraft.server.EntityTrackerEntry;

public class TrackingCommand extends Command {
	
	public TrackingCommand() {
        super("tracking");
        this.description = "Adjust player tracking distance";
        this.usageMessage = "/tracking <range>";
        setPermission("nspigot.command.tracking");
    }
    
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        
        if (args.length == 0) {
            for (World world : Bukkit.getWorlds()) {
                SpigotWorldConfig worldConfig = ((CraftWorld) world).getHandle().spigotConfig;
                sender.sendMessage(ChatColor.GOLD + "Player tracking distance in " + world.getName() + ": " + ChatColor.YELLOW + worldConfig.playerTrackingRange);
            }
            return true;
        }
        
        if (args.length == 1) {
            int range;
            try {
                range = Integer.parseInt(args[0]);
                if (range < 0) {
                    throw new NumberFormatException("Only positive numbers");
                }
            } catch (NumberFormatException ex) {
                sender.sendMessage(ChatColor.RED + "Range must be a positive integer");
                return false;
            }
            for (World world : Bukkit.getWorlds()) {
                SpigotWorldConfig worldConfig = ((CraftWorld) world).getHandle().spigotConfig;
                worldConfig.playerTrackingRange = range;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                EntityTracker tracker = ((CraftWorld) player.getWorld()).getHandle().getTracker();
                EntityTrackerEntry trackerEntry = (EntityTrackerEntry) tracker.trackedEntities.get(player.getEntityId());
                trackerEntry.b = range;
            }
            sender.sendMessage(ChatColor.GREEN + "Set player tracking distance for all worlds to: " + range);
            return true;
        }
        sender.sendMessage(ChatColor.RED + this.usageMessage);
        return false;
    }
}