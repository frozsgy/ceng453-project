package group10.client.enums;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Enumarator for player types.
 * Denotes the player's associated number.
 */
public enum PlayerEnum {
    /**
     * null player
     */
    NULL(0),
    /**
     * first player, player itself
     */
    ONE(1),
    /**
     * Second player, ai or human
     */
    TWO(2);

    /**
     * Id of this
     */
    private int id;

    /**
     * Constructor for this
     *
     * @param id id associated with enum.
     */
    PlayerEnum(int id) {
        this.id = id;
    }
}
