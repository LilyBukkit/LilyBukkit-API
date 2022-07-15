package org.bukkit.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.Server;

import org.bukkit.command.defaults.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import static org.bukkit.util.Java15Compat.Arrays_copyOfRange;

public final class SimpleCommandMap implements CommandMap {
    private final Map<String, Command> knownCommands = new HashMap<String, Command>();
    private final Set<String> aliases = new HashSet<String>();
    private final Server server;

    public SimpleCommandMap(final Server server) {
        this.server = server;
        setVanillaCommands();
        setDefaultCommands();
    }

    private void setDefaultCommands() {
        register("bukkit", new VersionCommand("version"));
        register("bukkit", new ReloadCommand("reload"));
        register("bukkit", new PluginsCommand("plugins"));
    }

    /**
     * {@inheritDoc}
     */
    public void registerAll(String fallbackPrefix, List<Command> commands) {
        if (commands != null) {
            for (Command c : commands) {
                register(fallbackPrefix, c);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean register(String fallbackPrefix, Command command) {
        return register(command.getName(), fallbackPrefix, command);
    }

    /**
     * {@inheritDoc}
     */
    public boolean register(String label, String fallbackPrefix, Command command) {
        boolean registeredPassedLabel = register(label, fallbackPrefix, command, false);

        Iterator iterator = command.getAliases().iterator();
        while (iterator.hasNext()) {
            if (!register((String) iterator.next(), fallbackPrefix, command, true)) {
                iterator.remove();
            }
        }

        // Register to us so further updates of the commands label and aliases are postponed until its reregistered
        command.register(this);

        return registeredPassedLabel;
    }

    /**
     * Registers a command with the given name is possible, otherwise uses fallbackPrefix to create a unique name if its not an alias
     * @param label the name of the command, without the '/'-prefix.
     * @param fallbackPrefix a prefix which is prepended to the command with a ':' one or more times to make the command unique
     * @param command the command to register
     * @return true if command was registered with the passed in label, false otherwise.
     * If isAlias was true a return of false indicates no command was registerd
     * If isAlias was false a return of false indicates the fallbackPrefix was used one or more times to create a unique name for the command
     */
    private synchronized boolean register(String label, String fallbackPrefix, Command command, boolean isAlias) {
        String lowerLabel = label.trim().toLowerCase();

        if (isAlias && knownCommands.containsKey(lowerLabel)) {
            // Request is for an alias and it conflicts with a existing command or previous alias ignore it
            // Note: This will mean it gets removed from the commands list of active aliases
            return false;
        }

        String lowerPrefix = fallbackPrefix.trim().toLowerCase();
        boolean registerdPassedLabel = true;

        // If the command exists but is an alias we overwrite it, otherwise we rename it based on the fallbackPrefix
        while (knownCommands.containsKey(lowerLabel) && !aliases.contains(lowerLabel)) {
            lowerLabel = lowerPrefix + ":" + lowerLabel;
            registerdPassedLabel = false;
        }

        if (isAlias) {
            aliases.add(lowerLabel);
        } else {
            // Ensure lowerLabel isn't listed as a alias anymore and update the commands registered name
            aliases.remove(lowerLabel);
            command.setLabel(lowerLabel);
        }
        knownCommands.put(lowerLabel, command);

        return registerdPassedLabel;
    }

    /**
     * {@inheritDoc}
     */
    public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
        String[] args = commandLine.split(" ");

        if (args.length == 0) {
            return false;
        }

        String sentCommandLabel = args[0].toLowerCase();
        Command target = getCommand(sentCommandLabel);
        if (target == null) {
            return false;
        }

        try {
            // Note: we don't return the result of target.execute as thats success / failure, we return handled (true) or not handled (false)
            target.execute(sender, sentCommandLabel, Arrays_copyOfRange(args, 1, args.length));
        } catch (CommandException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing '" + commandLine + "' in " + target, ex);
        }

        // return true as command was handled
        return true;
    }

    public synchronized void clearCommands() {
        for (Map.Entry<String, Command> entry : knownCommands.entrySet()) {
            entry.getValue().unregister(this);
        }
        knownCommands.clear();
        aliases.clear();
        setDefaultCommands();
        setVanillaCommands(); //LilyBukkit
    }

    public Command getCommand(String name) {
        return knownCommands.get(name.toLowerCase());
    }

    public void registerServerAliases() {
        Map<String, String[]> values = server.getCommandAliases();

        for (String alias : values.keySet()) {
            String[] targetNames = values.get(alias);
            List<Command> targets = new ArrayList<Command>();
            String bad = "";

            for (String name : targetNames) {
                Command command = getCommand(name);

                if (command == null) {
                    bad += name + ", ";
                } else {
                    targets.add(command);
                }
            }

            // We register these as commands so they have absolute priority.

            if (targets.size() > 0) {
                knownCommands.put(alias.toLowerCase(), new MultipleCommandAlias(alias.toLowerCase(), targets.toArray(new Command[0])));
            } else {
                knownCommands.remove(alias.toLowerCase());
            }

            if (bad.length() > 0) {
                bad = bad.substring(0, bad.length() - 2);
                server.getLogger().warning("The following command(s) could not be aliased under '" + alias + "' because they do not exist: " + bad);
            }
        }
    }

    //LilyBukkit
    private void setVanillaCommands(){
        register("minecraft", new HelpCommand());
        register("minecraft", new KickCommand());
        register("minecraft", new BanCommand());
        register("minecraft", new BanIpCommand());
        register("minecraft", new PardonCommand());
        register("minecraft", new PardonIpCommand());
        register("minecraft", new OpCommand());
        register("minecraft", new DeopCommand());
        register("minecraft", new TeleportCommand());
        register("minecraft", new GiveCommand());
        register("minecraft", new TellCommand());
        register("minecraft", new StopCommand());
        register("minecraft", new SaveCommand());
        register("minecraft", new SaveOffCommand());
        register("minecraft", new SaveOnCommand());
        register("minecraft", new ListCommand());
        register("minecraft", new SayCommand());
        register("minecraft", new MeCommand());
        register("minecraft", new WhitelistCommand());
    }
}
