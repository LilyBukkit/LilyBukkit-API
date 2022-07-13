package org.bukkit.entity;

/**
 * Represents a boat entity.
 *
 * @author sk89q
 */
public interface Boat extends Vehicle {

    /**
     * Gets the maximum speed of a boat. The speed is unrelated to the velocity.
     *
     */
    public double getMaxSpeed();

    /**
     * Sets the maximum speed of a boat. Must be nonnegative. Default is 0.4D.
     *
     */
    public void setMaxSpeed(double speed);
}
