package org.bukkit.event.entity;

import org.bukkit.event.Listener;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;

/**
 * Handles all events fired in relation to entities
 */
public class EntityListener implements Listener {
    public EntityListener() {
    }

    /**
     * Called when a creature is spawned into a world.
     * <p>
     * If a Creature Spawn event is cancelled, the creature will not spawn.
     * </p>
     *
     * @param event Relevant event details
     */
    public void onCreatureSpawn(CreatureSpawnEvent event) {
    }

    /**
     * Called when an item is spawned into a world
     *
     * @param event Relevant event details
     */
    public void onItemSpawn(ItemSpawnEvent event) {
    }

    /**
     * Called when an entity combusts due to the sun.
     * <p>
     * If an Entity Combust event is cancelled, the entity will not combust.
     * </p>
     *
     * @param event Relevant event details
     */
    public void onEntityCombust(EntityCombustEvent event) {
    }

    /**
     * Called when an entity is damaged
     *
     * @param event Relevant event details
     */
    public void onEntityDamage(EntityDamageEvent event) {
    }

    /**
     * Called when an entity explodes
     *
     * @param event Relevant event details
     */
    public void onEntityExplode(EntityExplodeEvent event) {
    }

    /**
     * Called when an entity's fuse is lit
     *
     * @param event Relevant event details
     */
    public void onExplosionPrime(ExplosionPrimeEvent event) {
    }

    /**
     * Called when an entity dies
     *
     * @param event Relevant event details
     */
    public void onEntityDeath(EntityDeathEvent event) {
    }

    /**
     * Called when a creature targets another entity
     *
     * @param event Relevant event details
     */
    public void onEntityTarget(EntityTargetEvent event) {
    }

    /**
     * Called when an entity interacts with an object
     *
     * @param event Relevant event details
     */
    public void onEntityInteract(EntityInteractEvent event) {
    }

    /**
     * Called when a painting is placed
     *
     * @param event Relevant event details
     */
    public void onPaintingPlace(PaintingPlaceEvent event) {
    }

    /**
     * Called when a painting is broken
     *
     * @param event Relevant event details
     */
    public void onPaintingBreak(PaintingBreakEvent event) {
    }

    /**
     * Called when an entity regains health (currently only applies to Players)
     *
     * @param event Relevant event details
     */
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
    }

    /**
     * Called when a project hits an object
     *
     * @param event Relevant event details
     */
    public void onProjectileHit(ProjectileHitEvent event) {
    }
}
