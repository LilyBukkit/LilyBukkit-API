package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * Called when a block is broken by a player.
 * <br>
 * Note:
 * Plugins wanting to simulate a traditional block drop should set the block to air and utilise their own methods for determining
 * what the default drop for the block being broken is and what to do about it, if anything.
 * <br>
 * If a Block Break event is cancelled, the block will not break.
 */
public class BlockBreakEvent extends BlockEvent implements Cancellable {

    private Player player;
    private boolean cancel;

    public BlockBreakEvent(final Block theBlock, Player player) {
        super(Type.BLOCK_BREAK, theBlock);
        this.player = player;
        this.cancel = false;
    }

    /**
     * Gets the Player that is breaking the block involved in this event.
     *
     * @return The Player that is breaking the block involved in this event
     */
    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
