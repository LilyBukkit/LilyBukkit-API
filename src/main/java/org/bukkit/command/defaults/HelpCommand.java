package org.bukkit.command.defaults;

import org.bukkit.command.CommandSender;

public class HelpCommand extends VanillaCommand {
    public HelpCommand() {
        super("help");
        this.description = "Shows the help menu";
        this.usageMessage = "/help";
        this.setPermission("bukkit.command.help");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage("help  or  ?               shows this message");
        sender.sendMessage("kick <player>             removes a player from the server");
        sender.sendMessage("ban <player>              bans a player from the server");
        sender.sendMessage("pardon <player>           pardons a banned player so that they can connect again");
        sender.sendMessage("ban-ip <ip>               bans an IP address from the server");
        sender.sendMessage("pardon-ip <ip>            pardons a banned IP address so that they can connect again");
        sender.sendMessage("op <player>               turns a player into an op");
        sender.sendMessage("deop <player>             removes op status from a player");
        sender.sendMessage("tp <player1> <player2>    moves one player to the same location as another player");
        sender.sendMessage("give <player> <id> [num]  gives a player a resource");
        sender.sendMessage("tell <player> <message>   sends a private message to a player");
        sender.sendMessage("stop                      gracefully stops the server");
        sender.sendMessage("save-all                  forces a server-wide level save");
        sender.sendMessage("save-off                  disables terrain saving (useful for backup scripts)");
        sender.sendMessage("save-on                   re-enables terrain saving");
        sender.sendMessage("list                      lists all currently connected players");
        sender.sendMessage("say <message>             broadcasts a message to all players");
        sender.sendMessage("me <message>              performs the specified action in chat");
        sender.sendMessage("whitelist                 manage server whitelist");

        return true;
    }

    @Override
    public boolean matches(String input) {
        return input.startsWith("help") || input.startsWith("?");
    }
}
