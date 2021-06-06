package group10.client.enums;

/**
 * Enumerator for player suit types.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public enum Suits {
    /**
     * Club suit
     */
    CLUB("C"),
    /**
     * Diamond suit
     */
    DIAMOND("D"),
    /**
     * Heart suit
     */
    HEART("H"),
    /**
     * Spade suit
     */
    SPADE("S");

    /**
     * Initials of the suit
     */
    private String initials;

    /**
     * Default constructor
     *
     * @param initials initials
     */
    Suits(String initials) {
        this.initials = initials;
    }

    /**
     * Gets initials
     *
     * @return initials
     */
    public String getInitials() {
        return initials;
    }
}
