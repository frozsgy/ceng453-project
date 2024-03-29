package group10.client.enums;

import java.io.Serializable;

/**
 * Enumerator for player types.
 * Denotes the player's associated number.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public enum PlayerEnum implements Serializable {
    /**
     * null player
     */
    NULL(0, "NULL"),
    /**
     * first player, player itself
     */
    ONE(1, "One"),
    /**
     * Second player, ai or human
     */
    TWO(2, "Two");

    /**
     * Id of this
     */
    private int id;

    /**
     * Name of this
     */
    private String name;


    /**
     * Constructor for this
     *
     * @param id id associated with enum.
     * @param name name of the enum
     */
    PlayerEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.name;
    }
}
