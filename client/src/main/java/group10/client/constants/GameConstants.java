package group10.client.constants;

/**
 *  @author Alperen Caykus, Mustafa Ozan Alpay
 *  Holds game related constants.
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
    /**
     * Defines the max score that needs to be performed before
     * passing to next level. Note that scoring a score more than
     * this value is possible. If this is the case, the game will continue
     * until all cards end.
     */
    public static final int MAX_SCORE = 151;
    /**
     * Defines how many points a pisti worths.
     */
    public final static int PISTI = 10;
    /**
     * Defines double pisti points.
     */
    public final static int DOUBLE_PISTI = 2 * PISTI;
}
