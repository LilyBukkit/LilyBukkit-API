package org.bukkit.command.defaults;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // LilyBukkit start
        Configuration config = new Configuration(new File("config/lilybukkit.yml"));
        boolean vanillaBehaviour = config.getBoolean("lilybukkit.vanilla-help", false); //until I figure out how to do this, the default should remain false
        if (!vanillaBehaviour) {
            //Array of Vanilla commands
            VanillaCommand[] vanillaCommands = new VanillaCommand[]{new HelpCommand(), new KickCommand(), new BanCommand(), new BanIpCommand(), new PardonCommand(), new PardonIpCommand(), new OpCommand(), new DeopCommand(), new TeleportCommand(), new GiveCommand(), new TellCommand(), new StopCommand(), new SaveCommand(), new SaveOnCommand(), new SaveOffCommand(), new ListCommand(), new SayCommand(), new MeCommand(), new WhitelistCommand()};
            //Array of built-in Bukkit commands
            Command[] defaultCommands = new Command[]{new VersionCommand("version"), new PluginsCommand("plugins"), new ReloadCommand("reload")};
            //Array of plugins' commands
            List<PluginCommand> pluginCommands = new ArrayList<>();
            //Filling the array. Yeah...
            for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                Map<String, Object> commandsRoot = new HashMap<String, Object>() {
                };
                commandsRoot.put("commands", plugin.getDescription().getCommands());
                ConfigurationNode commandsRootNode = new PluginConfigurationNode(commandsRoot);
                for (String commandKey : commandsRootNode.getNodes("commands").keySet()) {
                    pluginCommands.add(Bukkit.getServer().getPluginCommand(commandKey));
                }
            }

            // Outputting ALL existing commands
            for (int i = 0; i < vanillaCommands.length + defaultCommands.length + pluginCommands.size(); i++) {
                if (i < vanillaCommands.length) {
                    sender.sendMessage(vanillaCommands[i].getUsage() + " - " + vanillaCommands[i].getDescription());
                } else if (i > vanillaCommands.length && i < defaultCommands.length + vanillaCommands.length) {
                    sender.sendMessage(defaultCommands[i - vanillaCommands.length].getUsage() + " - " + defaultCommands[i - vanillaCommands.length].getDescription());
                } else if (i > vanillaCommands.length + defaultCommands.length) {
                    sender.sendMessage(pluginCommands.get(i - vanillaCommands.length - defaultCommands.length).getUsage() + " - " + pluginCommands.get(i - vanillaCommands.length - defaultCommands.length).getDescription());
                }
            }
        } else {
            // LilyBukkit end
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
        }
        return true;
    }

    @Override
    public boolean matches(String input) {
        return input.startsWith("help") || input.startsWith("?");
    }

    private class PluginConfigurationNode extends ConfigurationNode {
        public PluginConfigurationNode(Map<String, Object> root) {
            super(root);
        }
    }
}
