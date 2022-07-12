package org.bukkit.material;

/**
 * Indicated a Material that may carry or create a Greenstone current
 */
public interface Greenstone {

    /**
     * Gets the current state of this Material, indicating if it's powered or
     * unpowered
     *
     * @return true if powered, otherwise false
     */
    public boolean isPowered();
}
