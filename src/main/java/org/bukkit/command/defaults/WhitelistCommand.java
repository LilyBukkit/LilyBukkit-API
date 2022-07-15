package org.bukkit.command.defaults;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WhitelistCommand extends VanillaCommand {
    public WhitelistCommand() {
        super("whitelist");
        this.description = "Prevents the specified player from using this server";
        this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (badPerm(sender, "reload")) return true;

                Bukkit.getServer().reloadWhitelist();
                Command.broadcastCommandMessage(sender, "Reloaded white-list from file");
                return true;
            } else if (args[0].equalsIgnoreCase("on")) {
                if (badPerm(sender, "enable")) return true;

                Bukkit.getServer().setWhitelist(true);
                Command.broadcastCommandMessage(sender, "Turned on white-listing");
                return true;
            } else if (args[0].equalsIgnoreCase("off")) {
                if (badPerm(sender, "disable")) return true;

                Bukkit.getServer().setWhitelist(false);
                Command.broadcastCommandMessage(sender, "Turned off white-listing");
                return true;
            } else if (args[0].equalsIgnoreCase("list")) {
                if (badPerm(sender, "list")) return true;

                StringBuilder result = new StringBuilder();

                for (OfflinePlayer player : Bukkit.getServer().getWhitelistedPlayers()) {
                    if (result.length() > 0) {
                        result.append(" ");
                    }

                    result.append(player.getName());
                }

                sender.sendMessage("White-listed players: " + result.toString());
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (badPerm(sender, "add")) return true;

                Bukkit.getServer().getOfflinePlayer(args[1]).setWhitelisted(true);

                Command.broadcastCommandMessage(sender, "Added " + args[1] + " to white-list");
                return true;
            } else if (args[0].equalsIgnoreCase("remove")) {
                if (badPerm(sender, "remove")) return true;

                Bukkit.getServer().getOfflinePlayer(args[1]).setWhitelisted(false);

                Command.broadcastCommandMessage(sender, "Removed " + args[1] + " from white-list");
                return true;
            }
            //Rosepad support
            else if (args[0].equalsIgnoreCase("add-ip")) {
                if (badPerm(sender, "add-ip")) return true;

                Bukkit.getServer().getOfflinePlayer(args[1]).setIPWhitelisted(true);

                Command.broadcastCommandMessage(sender, "Added " + args[1] + "'s IP to whitelist");
            } else if (args[0].equalsIgnoreCase("remove-ip")) {
                if (badPerm(sender, "remove-ip")) return true;

                Bukkit.getServer().getOfflinePlayer(args[1]).setIPWhitelisted(false);

                Command.broadcastCommandMessage(sender, "Removed " + args[1] + "'s IP from the whitelist");
            }
        }

        sender.sendMessage(ChatColor.RED + "Correct command usage:\n" + usageMessage);
        return false;
    }

    private boolean badPerm(CommandSender sender, String perm) {
        if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action.");
            return true;
        }

        return false;
    }

    @Override
    public boolean matches(String input) {
        return input.startsWith("whitelist ") || input.equalsIgnoreCase("whitelist");
    }
}
