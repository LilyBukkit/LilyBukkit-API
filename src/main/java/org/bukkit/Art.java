package org.bukkit;

import java.util.HashMap;

/**
 * Represents the art on a painting
 */
public enum Art {
    KEBAB(0, 1, 1),
    AZTEC(1, 1, 1),
    ALBAN(2, 1, 1),
    AZTEC2(3, 1, 1),
    BOMB(4, 1, 1),
    PLANT(5, 1, 1),
    WASTELAND(6, 1, 1),
    POOL(7, 2, 1),
    COURBET(8, 2, 1),
    SEA(9, 2, 1),
    SUNSET(10, 2, 1),
    WANDERER(12, 1, 2),
    MATCH(14, 4, 2),
    BUST(15, 2, 2),
    STAGE(16, 2, 2),
    VOID(17, 2, 2),
    ANGEL(18, 2, 2),
    SKULL_AND_ROSES(19, 2, 2),
    POINTER(20, 4, 4),
    //In case the client uses the new paintings
    SOLDIER_TF2(0, 1, 1),
    SMOKE(1, 1, 1),
    CRAB_NEBULA(2, 1, 1),
    CITY_LANDSCAPE(3, 1, 1),
    BLACK_AND_WHITE(4, 1, 1),
    BLURRED(5, 1, 1),
    DIM_POT(6, 1, 1),
    DEMO_SIGN(7, 2, 1),
    CORRUPTED_STEVE(8, 2, 1),
    CHANGED_SEA(9, 2, 1),
    NOISE(10, 2, 1),
    LERMONTOV(12, 1, 2),
    HUT(14, 4, 2),
    CAVE(15, 2, 2),
    RAVINE_ENTRANCE(16, 2, 2),
    BLOCK(17, 2, 2),
    WASTELAND_LANDSCAPE(18, 2, 2),
    COLORED_SQUARES(19, 2, 2),
    HUB_DOORS(20, 4, 4);
    private int id, width, height;
    private static HashMap<String, Art> names = new HashMap<>();
    private static HashMap<Integer, Art> ids = new HashMap<>();

    static {
        for (Art art : Art.values()) {
            ids.put(art.id, art);
            names.put(art.toString(), art);
        }
    }

    private Art(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the painting, in blocks
     *
     * @return The width of the painting, in blocks
     */
    public int getBlockWidth() {
        return width;
    }

    /**
     * Gets the height of the painting, in blocks
     *
     * @return The height of the painting, in blocks
     */
    public int getBlockHeight() {
        return height;
    }

    /**
     * Get the ID of this painting.
     *
     * @return The ID of this painting
     */
    public int getId() {
        return id;
    }

    /**
     * Get a painting by its numeric ID
     *
     * @param id The ID
     * @return The painting
     */
    public static Art getById(int id) {
        return ids.get(id);
    }

    /**
     * Get a painting by its unique name
     *
     * @param name The name
     * @return The painting
     */
    public static Art getByName(String name) {
        return names.get(name);
    }
}
