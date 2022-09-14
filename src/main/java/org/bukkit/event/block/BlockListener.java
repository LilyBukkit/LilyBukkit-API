package org.bukkit.event.block;

import org.bukkit.event.Listener;

/**
 * Handles all events thrown in relation to Blocks
 */
public class BlockListener implements Listener {

    /**
     * Default Constructor
     */
    public BlockListener() {}

    /**
     * Called when a block is damaged by a player.
     * <br>
     * If a Block Damage event is cancelled, the block will not be damaged.
     *
     * @param event Relevant event details
     */
    public void onBlockDamage(BlockDamageEvent event) {}

    /**
     * Called when we try to place a block, to see if we can build it here or not.
     *<br>
     * Note:
     * <ul>
     *    <li>The Block returned by getBlock() is the block we are trying to place on, not the block we are trying to place.</li>
     *    <li>If you want to figure out what is being placed, use {@link BlockCanBuildEvent#getMaterial()} or {@link BlockCanBuildEvent#getMaterialId()} instead.</li>
     * </ul>
     *
     * @param event Relevant event details
     */
    public void onBlockCanBuild(BlockCanBuildEvent event) {}

    /**
     * Represents events with a source block and a destination block, currently only applies to liquid (lava and water).
     *<br>
     * If a Block From To event is cancelled, the block will not move (the liquid will not flow).
     *
     * @param event Relevant event details
     */
    public void onBlockFromTo(BlockFromToEvent event) {}

    /**
     * Called when a block is ignited. If you want to catch when a Player places fire, you need to use {@link BlockPlaceEvent}.
     *<br>
     * If a Block Ignite event is cancelled, the block will not be ignited.
     *
     * @param event Relevant event details
     */
    public void onBlockIgnite(BlockIgniteEvent event) {}

    /**
     * Called when block physics occurs.
     *
     * @param event Relevant event details
     */
    public void onBlockPhysics(BlockPhysicsEvent event) {}

    /**
     * Called when a block is placed by a player.
     *<br>
     * If a Block Place event is cancelled, the block will not be placed.
     *
     * @param event Relevant event details
     */
    public void onBlockPlace(BlockPlaceEvent event) {}

    /**
     * Called when Greenstone changes.<br>
     * From: the source of the Greenstone change.<br>
     * To: The Greenstone dust that changed.
     *
     * @param event Relevant event details
     */
    public void onBlockGreenstoneChange(BlockGreenstoneEvent event) {}

    /**
     * Called when leaves are decaying naturally.
     *<br>
     * If a Leaves Decay event is cancelled, the leaves will not decay.
     *
     * @param event Relevant event details
     */
    public void onLeavesDecay(LeavesDecayEvent event) {}

    /**
     * Called when a sign is changed by a player.
     * <br>
     * If a Sign Change event is cancelled, the sign will not be changed.
     *
     * @param event Relevant event details
     */
    public void onSignChange(SignChangeEvent event) {}

    /**
     * Called when a block is destroyed as a result of being burnt by fire.
     *<br>
     * If a Block Burn event is cancelled, the block will not be destroyed as a result of being burnt by fire.
     *
     * @param event Relevant event details
     */
    public void onBlockBurn(BlockBurnEvent event) {}

    /**
     * Called when a block is broken by a player.
     *<br>
     * Note:
     * Plugins wanting to simulate a traditional block drop should set the block to air and utilise their own methods for determining
     *   what the default drop for the block being broken is and what to do about it, if anything.
     *<br>
     * If a Block Break event is cancelled, the block will not break.
     *
     * @param event Relevant event details
     */
    public void onBlockBreak(BlockBreakEvent event) {}

    /**
     * Called when a block is formed or spreads based on world conditions.
     * Use {@link BlockSpreadEvent} to catch blocks that actually spread and don't just "randomly" form.
     *<br>
     * Examples:
     *<ul>
     *     <li>Snow forming due to a snow storm.</li>
     *     <li>Ice forming in a snowy Biome like Tiga or Tundra.</li>
     * </ul>
     *<br>
     * If a Block Form event is cancelled, the block will not be formed or will not spread.
     *
     * @see BlockSpreadEvent
     * @param event Relevant event details
     */
    public void onBlockForm(BlockFormEvent event) {}

    /**
     * Called when a block spreads based on world conditions.
     * Use {@link BlockFormEvent} to catch blocks that "randomly" form instead of actually spread.
     *<br>
     * Examples:
     *<ul>
     *     <li>Mushrooms spreading.</li>
     *     <li>Fire spreading.</li>
     * </ul>
     *<br>
     * If a Block Spread event is cancelled, the block will not spread.
     *
     * @param event Relevant event details
     */
    public void onBlockSpread(BlockSpreadEvent event) {}

    /**
     * Called when a block fades, melts or disappears based on world conditions
     * <br>
     * Examples:
     * <ul>
     *     <li>Snow melting due to being near a light source.</li>
     *     <li>Ice melting due to being near a light source.</li>
     * </ul>
     * <br>
     * If a Block Fade event is cancelled, the block will not fade, melt or disappear.
     *
     * @param event Relevant event details
     */
    public void onBlockFade(BlockFadeEvent event) {}

    /**
     * Called when an item is dispensed from a block.
     *<br>
     * If a Block Dispense event is cancelled, the block will not dispense the item.
     *
     * @param event Relevant event details
     */
    public void onBlockDispense(BlockDispenseEvent event) {}
}
