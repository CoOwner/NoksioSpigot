package us.noks.rinny.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class KnockbackCommand extends Command {

    public KnockbackCommand(String name) {
        super(name);
        description = "Settup or get knockback of the server";
        usageMessage = "/setknockback [reset|get|sprintkbforce|sprintkbheight|resetsprint] <value>";
        setPermission("nspigot.command.knockback");
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!testPermission(sender))
            return true;

        if (args.length > 2 || args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + usageMessage);
            return false;
        }

        if (args.length == 2) {
            sender.sendMessage(ChatColor.RED + "Soon...");
            return false;
        }

        return true;
    }
}