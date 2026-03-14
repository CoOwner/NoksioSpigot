package us.noks.rinny.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CreatorsCommand extends Command {
	
    public CreatorsCommand(String name) {
        super(name);
        this.description = "To know who's made this PaperSpigot";
        this.usageMessage = "/creator";
        this.setAliases(Arrays.asList("creators"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (args.length > 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }
        
        sender.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "Creators of RinnySpigot:\n" + 
        				   ChatColor.RED + "Lead Developer: " + ChatColor.DARK_AQUA + "Noksio\n" + 
        				   ChatColor.GOLD + "Developer: " + ChatColor.AQUA + "QcDev\n" + 
        				   ChatColor.GOLD + "Developer: " + ChatColor.AQUA + "Riotten\n" +
        				   ChatColor.GOLD + "Jr. Developer: " + ChatColor.AQUA + "Dreamzy");
        return true;
    }
}
