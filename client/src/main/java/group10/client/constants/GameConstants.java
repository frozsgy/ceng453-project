package group10.client.constants;

/**
 * Holds game related constants.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class GameConstants {

    /**
     * Defines how many cars are allowed per hand
     */
    public static final int CARD_PER_HAND = 4;
    /**
     * Defines the last playable round of the game.
     */
    public static final int LAST_ROUND = 3;

    public static final int MULIPLAYER_LEVEL = LAST_ROUND + 1;
    /**
     * Defines the max score that needs to be performed before
     * passing to next level. Note that scoring a score more than
     * this value is possible. If this is the case, the game will continue
     * until all cards end.
     */
    public static final int MAX_SCORE = 11; //TODO make this 151
    /**
     * Defines how many points a pisti worth.
     */
    public final static int PISTI = 10;
    /**
     * Defines double pisti points.
     */
    public final static int DOUBLE_PISTI = 2 * PISTI;

    public final static int MULTIPLAYER_IDLE_MS = 3000;
}
