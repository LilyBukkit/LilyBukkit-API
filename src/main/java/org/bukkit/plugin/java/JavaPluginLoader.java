package org.bukkit.plugin.java;

import org.bukkit.Server;
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGreenstoneEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryListener;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleListener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.UnknownDependencyException;
import org.bukkit.plugin.UnknownSoftDependencyException;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Represents a Java plugin loader, allowing plugins in the form of .jar
 */
public class JavaPluginLoader implements PluginLoader {
    private final Server server;
    protected final Pattern[] fileFilters = new Pattern[]{
            Pattern.compile("\\.jar$"),
    };
    protected final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    protected final Map<String, PluginClassLoader> loaders = new HashMap<String, PluginClassLoader>();

    public JavaPluginLoader(Server instance) {
        server = instance;
    }

    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
        return loadPlugin(file, false);
    }

    public Plugin loadPlugin(File file, boolean ignoreSoftDependencies) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
        JavaPlugin result = null;
        PluginDescriptionFile description = null;

        if (!file.exists()) {
            throw new InvalidPluginException(new FileNotFoundException(String.format("%s does not exist", file.getPath())));
        }
        try {
            JarFile jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("plugin.yml");

            if (entry == null) {
                throw new InvalidPluginException(new FileNotFoundException("Jar does not contain plugin.yml"));
            }

            InputStream stream = jar.getInputStream(entry);

            description = new PluginDescriptionFile(stream);

            stream.close();
            jar.close();
        } catch (IOException ex) {
            throw new InvalidPluginException(ex);
        } catch (YAMLException ex) {
            throw new InvalidPluginException(ex);
        }

        File dataFolder = new File(file.getParentFile(), description.getName());
        File oldDataFolder = getDataFolder(file);

        // Found old data folder
        if (dataFolder.equals(oldDataFolder)) {
            // They are equal -- nothing needs to be done!
        } else if (dataFolder.isDirectory() && oldDataFolder.isDirectory()) {
            server.getLogger().log(Level.INFO, String.format(
                    "While loading %s (%s) found old-data folder: %s next to the new one: %s",
                    description.getName(),
                    file,
                    oldDataFolder,
                    dataFolder
            ));
        } else if (oldDataFolder.isDirectory() && !dataFolder.exists()) {
            if (!oldDataFolder.renameTo(dataFolder)) {
                throw new InvalidPluginException(new Exception("Unable to rename old data folder: '" + oldDataFolder + "' to: '" + dataFolder + "'"));
            }
            server.getLogger().log(Level.INFO, String.format(
                    "While loading %s (%s) renamed data folder: '%s' to '%s'",
                    description.getName(),
                    file,
                    oldDataFolder,
                    dataFolder
            ));
        }

        if (dataFolder.exists() && !dataFolder.isDirectory()) {
            throw new InvalidPluginException(new Exception(String.format(
                    "Projected datafolder: '%s' for %s (%s) exists and is not a directory",
                    dataFolder,
                    description.getName(),
                    file
            )));
        }

        ArrayList<String> depend;

        try {
            depend = (ArrayList) description.getDepend();
            if (depend == null) {
                depend = new ArrayList<String>();
            }
        } catch (ClassCastException ex) {
            throw new InvalidPluginException(ex);
        }

        for (String pluginName : depend) {
            if (loaders == null) {
                throw new UnknownDependencyException(pluginName);
            }
            PluginClassLoader current = loaders.get(pluginName);

            if (current == null) {
                throw new UnknownDependencyException(pluginName);
            }
        }

        if (!ignoreSoftDependencies) {
            ArrayList<String> softDepend;

            try {
                softDepend = (ArrayList) description.getSoftDepend();
                if (softDepend == null) {
                    softDepend = new ArrayList<String>();
                }
            } catch (ClassCastException ex) {
                throw new InvalidPluginException(ex);
            }

            for (String pluginName : softDepend) {
                if (loaders == null) {
                    throw new UnknownSoftDependencyException(pluginName);
                }
                PluginClassLoader current = loaders.get(pluginName);

                if (current == null) {
                    throw new UnknownSoftDependencyException(pluginName);
                }
            }
        }

        PluginClassLoader loader = null;

        try {
            URL[] urls = new URL[1];

            urls[0] = file.toURI().toURL();
            loader = new PluginClassLoader(this, urls, getClass().getClassLoader());
            Class<?> jarClass = Class.forName(description.getMain(), true, loader);
            Class<? extends JavaPlugin> plugin = jarClass.asSubclass(JavaPlugin.class);

            Constructor<? extends JavaPlugin> constructor = plugin.getConstructor();

            result = constructor.newInstance();

            result.initialize(this, server, description, dataFolder, file, loader);
        } catch (Throwable ex) {
            throw new InvalidPluginException(ex);
        }

        loaders.put(description.getName(), loader);

        return result;
    }

    protected File getDataFolder(File file) {
        File dataFolder = null;

        String filename = file.getName();
        int index = file.getName().lastIndexOf(".");

        if (index != -1) {
            String name = filename.substring(0, index);

            dataFolder = new File(file.getParentFile(), name);
        } else {
            // This is if there is no extension, which should not happen
            // Using _ to prevent name collision

            dataFolder = new File(file.getParentFile(), filename + "_");
        }

        return dataFolder;
    }

    public Pattern[] getPluginFileFilters() {
        return fileFilters;
    }

    public Class<?> getClassByName(final String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (String current : loaders.keySet()) {
                PluginClassLoader loader = loaders.get(current);

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException ignored) {
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    public void setClass(final String name, final Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    public EventExecutor createExecutor(Event.Type type, Listener listener) {
        // TODO: remove multiple Listener type and hence casts

        switch (type) {
            // Player Events

            case PLAYER_JOIN:
                return (listener173, event) -> ((PlayerListener) listener173).onPlayerJoin((PlayerJoinEvent) event);

            case PLAYER_QUIT:
                return (listener172, event) -> ((PlayerListener) listener172).onPlayerQuit((PlayerQuitEvent) event);

            case PLAYER_RESPAWN:
                return (listener171, event) -> ((PlayerListener) listener171).onPlayerRespawn((PlayerRespawnEvent) event);

            case PLAYER_KICK:
                return (listener170, event) -> ((PlayerListener) listener170).onPlayerKick((PlayerKickEvent) event);

            case PLAYER_COMMAND_PREPROCESS:
                return (listener169, event) -> ((PlayerListener) listener169).onPlayerCommandPreprocess((PlayerCommandPreprocessEvent) event);

            case PLAYER_CHAT:
                return (listener168, event) -> ((PlayerListener) listener168).onPlayerChat((PlayerChatEvent) event);

            case PLAYER_MOVE:
                return (listener167, event) -> ((PlayerListener) listener167).onPlayerMove((PlayerMoveEvent) event);

            case PLAYER_VELOCITY:
                return (listener166, event) -> ((PlayerListener) listener166).onPlayerVelocity((PlayerVelocityEvent) event);

            case PLAYER_TELEPORT:
                return (listener165, event) -> ((PlayerListener) listener165).onPlayerTeleport((PlayerTeleportEvent) event);

            case PLAYER_INTERACT:
                return (listener164, event) -> ((PlayerListener) listener164).onPlayerInteract((PlayerInteractEvent) event);

            case PLAYER_INTERACT_ENTITY:
                return (listener163, event) -> ((PlayerListener) listener163).onPlayerInteractEntity((PlayerInteractEntityEvent) event);

            case PLAYER_LOGIN:
                return (listener162, event) -> ((PlayerListener) listener162).onPlayerLogin((PlayerLoginEvent) event);

            case PLAYER_PRELOGIN:
                return (listener161, event) -> ((PlayerListener) listener161).onPlayerPreLogin((PlayerPreLoginEvent) event);

            case PLAYER_ANIMATION:
                return (listener160, event) -> ((PlayerListener) listener160).onPlayerAnimation((PlayerAnimationEvent) event);

            case INVENTORY_OPEN:
                return (listener159, event) -> ((PlayerListener) listener159).onInventoryOpen((PlayerInventoryEvent) event);

            case PLAYER_ITEM_HELD:
                return (listener158, event) -> ((PlayerListener) listener158).onItemHeldChange((PlayerItemHeldEvent) event);

            case PLAYER_DROP_ITEM:
                return (listener157, event) -> ((PlayerListener) listener157).onPlayerDropItem((PlayerDropItemEvent) event);

            case PLAYER_PICKUP_ITEM:
                return (listener156, event) -> ((PlayerListener) listener156).onPlayerPickupItem((PlayerPickupItemEvent) event);

            case PLAYER_BUCKET_EMPTY:
                return (listener155, event) -> ((PlayerListener) listener155).onPlayerBucketEmpty((PlayerBucketEmptyEvent) event);

            case PLAYER_BUCKET_FILL:
                return (listener154, event) -> ((PlayerListener) listener154).onPlayerBucketFill((PlayerBucketFillEvent) event);

            case PLAYER_CHANGED_WORLD:
                return (listener153, event) -> ((PlayerListener) listener153).onPlayerChangedWorld((PlayerChangedWorldEvent) event);

            // Block Events
            case BLOCK_PHYSICS:
                return (listener152, event) -> ((BlockListener) listener152).onBlockPhysics((BlockPhysicsEvent) event);

            case BLOCK_CANBUILD:
                return (listener151, event) -> ((BlockListener) listener151).onBlockCanBuild((BlockCanBuildEvent) event);

            case BLOCK_PLACE:
                return (listener150, event) -> ((BlockListener) listener150).onBlockPlace((BlockPlaceEvent) event);

            case BLOCK_DAMAGE:
                return (listener149, event) -> ((BlockListener) listener149).onBlockDamage((BlockDamageEvent) event);

            case BLOCK_FROMTO:
                return (listener148, event) -> ((BlockListener) listener148).onBlockFromTo((BlockFromToEvent) event);

            case LEAVES_DECAY:
                return (listener147, event) -> ((BlockListener) listener147).onLeavesDecay((LeavesDecayEvent) event);

            case SIGN_CHANGE:
                return (listener146, event) -> ((BlockListener) listener146).onSignChange((SignChangeEvent) event);

            case BLOCK_IGNITE:
                return (listener145, event) -> ((BlockListener) listener145).onBlockIgnite((BlockIgniteEvent) event);

            case GREENSTONE_CHANGE:
                return (listener144, event) -> ((BlockListener) listener144).onBlockGreenstoneChange((BlockGreenstoneEvent) event);

            case BLOCK_BURN:
                return (listener143, event) -> ((BlockListener) listener143).onBlockBurn((BlockBurnEvent) event);

            case BLOCK_BREAK:
                return (listener142, event) -> ((BlockListener) listener142).onBlockBreak((BlockBreakEvent) event);

            case BLOCK_FORM:
                return (listener141, event) -> ((BlockListener) listener141).onBlockForm((BlockFormEvent) event);

            case BLOCK_SPREAD:
                return (listener140, event) -> ((BlockListener) listener140).onBlockSpread((BlockSpreadEvent) event);


            case BLOCK_FADE:
                return (listener139, event) -> ((BlockListener) listener139).onBlockFade((BlockFadeEvent) event);

            case BLOCK_DISPENSE:
                return (listener138, event) -> ((BlockListener) listener138).onBlockDispense((BlockDispenseEvent) event);

            // Server Events
            case PLUGIN_ENABLE:
                return (listener137, event) -> ((ServerListener) listener137).onPluginEnable((PluginEnableEvent) event);

            case PLUGIN_DISABLE:
                return (listener136, event) -> ((ServerListener) listener136).onPluginDisable((PluginDisableEvent) event);

            case SERVER_COMMAND:
                return (listener135, event) -> ((ServerListener) listener135).onServerCommand((ServerCommandEvent) event);

            // World Events
            case CHUNK_LOAD:
                return (listener134, event) -> ((WorldListener) listener134).onChunkLoad((ChunkLoadEvent) event);

            case CHUNK_POPULATED:
                return (listener133, event) -> ((WorldListener) listener133).onChunkPopulate((ChunkPopulateEvent) event);

            case CHUNK_UNLOAD:
                return (listener132, event) -> ((WorldListener) listener132).onChunkUnload((ChunkUnloadEvent) event);

            case SPAWN_CHANGE:
                return (listener131, event) -> ((WorldListener) listener131).onSpawnChange((SpawnChangeEvent) event);

            case WORLD_SAVE:
                return (listener130, event) -> ((WorldListener) listener130).onWorldSave((WorldSaveEvent) event);

            case WORLD_INIT:
                return (listener129, event) -> ((WorldListener) listener129).onWorldInit((WorldInitEvent) event);

            case WORLD_LOAD:
                return (listener128, event) -> ((WorldListener) listener128).onWorldLoad((WorldLoadEvent) event);

            case WORLD_UNLOAD:
                return (listener127, event) -> ((WorldListener) listener127).onWorldUnload((WorldUnloadEvent) event);

            // Painting Events
            case PAINTING_PLACE:
                return (listener126, event) -> ((EntityListener) listener126).onPaintingPlace((PaintingPlaceEvent) event);

            case PAINTING_BREAK:
                return (listener125, event) -> ((EntityListener) listener125).onPaintingBreak((PaintingBreakEvent) event);

            // Entity Events
            case ENTITY_DAMAGE:
                return (listener124, event) -> ((EntityListener) listener124).onEntityDamage((EntityDamageEvent) event);

            case ENTITY_DEATH:
                return (listener123, event) -> ((EntityListener) listener123).onEntityDeath((EntityDeathEvent) event);

            case ENTITY_COMBUST:
                return (listener122, event) -> ((EntityListener) listener122).onEntityCombust((EntityCombustEvent) event);

            case ENTITY_EXPLODE:
                return (listener121, event) -> ((EntityListener) listener121).onEntityExplode((EntityExplodeEvent) event);

            case EXPLOSION_PRIME:
                return (listener120, event) -> ((EntityListener) listener120).onExplosionPrime((ExplosionPrimeEvent) event);

            case ENTITY_TARGET:
                return (listener119, event) -> ((EntityListener) listener119).onEntityTarget((EntityTargetEvent) event);

            case ENTITY_INTERACT:
                return (listener118, event) -> ((EntityListener) listener118).onEntityInteract((EntityInteractEvent) event);

            case CREATURE_SPAWN:
                return (listener117, event) -> ((EntityListener) listener117).onCreatureSpawn((CreatureSpawnEvent) event);

            case ITEM_SPAWN:
                return (listener116, event) -> ((EntityListener) listener116).onItemSpawn((ItemSpawnEvent) event);

            case ENTITY_REGAIN_HEALTH:
                return (listener115, event) -> ((EntityListener) listener115).onEntityRegainHealth((EntityRegainHealthEvent) event);

            case PROJECTILE_HIT:
                return (listener114, event) -> ((EntityListener) listener114).onProjectileHit((ProjectileHitEvent) event);

            // Vehicle Events
            case VEHICLE_CREATE:
                return (listener113, event) -> ((VehicleListener) listener113).onVehicleCreate((VehicleCreateEvent) event);

            case VEHICLE_DAMAGE:
                return (listener112, event) -> ((VehicleListener) listener112).onVehicleDamage((VehicleDamageEvent) event);

            case VEHICLE_DESTROY:
                return (listener111, event) -> ((VehicleListener) listener111).onVehicleDestroy((VehicleDestroyEvent) event);

            case VEHICLE_COLLISION_BLOCK:
                return (listener110, event) -> ((VehicleListener) listener110).onVehicleBlockCollision((VehicleBlockCollisionEvent) event);

            case VEHICLE_COLLISION_ENTITY:
                return (listener19, event) -> ((VehicleListener) listener19).onVehicleEntityCollision((VehicleEntityCollisionEvent) event);

            case VEHICLE_ENTER:
                return (listener18, event) -> ((VehicleListener) listener18).onVehicleEnter((VehicleEnterEvent) event);

            case VEHICLE_EXIT:
                return (listener17, event) -> ((VehicleListener) listener17).onVehicleExit((VehicleExitEvent) event);

            case VEHICLE_MOVE:
                return (listener16, event) -> ((VehicleListener) listener16).onVehicleMove((VehicleMoveEvent) event);

            case VEHICLE_UPDATE:
                return (listener15, event) -> ((VehicleListener) listener15).onVehicleUpdate((VehicleUpdateEvent) event);

            // Inventory Events
            case FURNACE_SMELT:
                return (listener14, event) -> ((InventoryListener) listener14).onFurnaceSmelt((FurnaceSmeltEvent) event);
            case FURNACE_BURN:
                return (listener13, event) -> ((InventoryListener) listener13).onFurnaceBurn((FurnaceBurnEvent) event);

            // Custom Events
            case CUSTOM_EVENT:
                return (listener1, event) -> ((CustomEventListener) listener1).onCustomEvent(event);
        }

        throw new IllegalArgumentException("Event " + type + " is not supported");
    }

    public void enablePlugin(final Plugin plugin) {
        if (!(plugin instanceof JavaPlugin)) {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }

        if (!plugin.isEnabled()) {
            JavaPlugin jPlugin = (JavaPlugin) plugin;

            String pluginName = jPlugin.getDescription().getName();

            if (!loaders.containsKey(pluginName)) {
                loaders.put(pluginName, (PluginClassLoader) jPlugin.getClassLoader());
            }

            try {
                jPlugin.setEnabled(true);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred while enabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
            }

            // Perhaps abort here, rather than continue going, but as it stands,
            // an abort is not possible the way it's currently written
            server.getPluginManager().callEvent(new PluginEnableEvent(plugin));
        }
    }

    public void disablePlugin(Plugin plugin) {
        if (!(plugin instanceof JavaPlugin)) {
            throw new IllegalArgumentException("Plugin is not associated with this PluginLoader");
        }

        if (plugin.isEnabled()) {
            JavaPlugin jPlugin = (JavaPlugin) plugin;
            ClassLoader cloader = jPlugin.getClassLoader();

            try {
                jPlugin.setEnabled(false);
            } catch (Throwable ex) {
                server.getLogger().log(Level.SEVERE, "Error occurred while disabling " + plugin.getDescription().getFullName() + " (Is it up to date?): " + ex.getMessage(), ex);
            }

            server.getPluginManager().callEvent(new PluginDisableEvent(plugin));

            loaders.remove(jPlugin.getDescription().getName());

            if (cloader instanceof PluginClassLoader) {
                PluginClassLoader loader = (PluginClassLoader) cloader;
                Set<String> names = loader.getClasses();

                for (String name : names) {
                    classes.remove(name);
                }
            }
        }
    }
}
