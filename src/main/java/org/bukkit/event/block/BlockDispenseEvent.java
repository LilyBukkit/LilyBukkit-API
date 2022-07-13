package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Called when an item is dispensed from a block.
 *<br>
 * If a Block Dispense event is cancelled, the block will not dispense the item.
 */
public class BlockDispenseEvent extends BlockEvent implements Cancellable {

    private boolean cancelled = false;
    private ItemStack item;
    private Vector velocity;

    public BlockDispenseEvent(Block block, ItemStack dispensed, Vector velocity) {
        super(Type.BLOCK_DISPENSE, block);
        this.item = dispensed;
        this.velocity = velocity;
    }

    /**
     * Gets the item that is being dispensed. Modifying the returned item
     * will have no effect, you must use {@link #setItem(ItemStack)} instead.
     *
     * @return An ItemStack for the item being dispensed
     */
    public ItemStack getItem() {
        return item.clone();
    }

    /**
     * Sets the item being dispensed.
     *
     * @param item the item being dispensed
     */
    public void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * Gets the velocity.
     *<p>
     * Note: Modifying the returned Vector will not change the velocity, you must use {@link #setVelocity(Vector)} instead.
     *
     * @return A Vector for the dispensed item's velocity
     */
    public Vector getVelocity() {
        return velocity.clone();
    }

    /**
     * Sets the velocity of the item being dispensed.
     *
     * @param vel the velocity of the item being dispensed
     */
    public void setVelocity(Vector vel) {
        velocity = vel;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
