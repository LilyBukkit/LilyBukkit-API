package org.bukkit.event.entity;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Thrown whenever a {@link Player} dies
 */
public class PlayerDeathEvent extends EntityDeathEvent {
    private String deathMessage = "";

    public PlayerDeathEvent(Player player, List<ItemStack> drops, String deathMessage) {
        super(player, drops);
        this.deathMessage = deathMessage;
    }

    /**
     * Set the death message that will appear to everyone on the server.
     *
     * @param deathMessage Message to appear to other players on the server.
     */
    public void setDeathMessage(String deathMessage) {
        this.deathMessage = deathMessage;
    }

    /**
     * Get the death message that will appear to everyone on the server.
     *
     * @return Message to appear to other players on the server.
     */
    public String getDeathMessage() {
        return this.deathMessage;
    }
}