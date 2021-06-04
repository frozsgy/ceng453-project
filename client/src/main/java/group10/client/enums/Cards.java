package group10.client.enums;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Enumarator for card types. Holds card names(numbers) and points associated with them.
 */
public enum Cards {
    /**
     * ACE, name: A, points: 1
     */
    ACE("A", 1),
    /**
     * TWO: name: 2, points: 0
     */
    TWO("2", 0),
    /**
     * THREE: name: 3, points: 0
     */
    THREE("3", 0),
    /**
     * FOUR: name: 4, points, 0
     */
    FOUR("4", 0),
    /**
     * FIVE: name: 5, points 0
     */
    FIVE("5", 0),
    /**
     * SIX: name: 6, points 0
     */
    SIX("6", 0),
    /**
     * SEVEN: name: 7, points 0
     */
    SEVEN("7", 0),
    /**
     * EIGHT: name: 8, points 0
     */
    EIGHT("8", 0),
    /**
     * NINE: name: 9, points 0
     */
    NINE("9", 0),
    /**
     * TEN: name: 10, points 0
     */
    TEN("10", 0),
    /**
     * JACK: name: J, points: 1
     */
    JACK("J", 1),
    /**
     * QUEEN: name: Q, points 0
     */
    QUEEN("Q", 0),
    /**
     * KING: name: K, points 0
     */
    KING("K", 0);

    /**
     * Name(number) of this card.
     */
    private String name;
    /**
     * Points that player gets upon capturing this card.
     * Denotes how many points this card contribute to points.
     */
    private int points;

    /**
     * Constructor for card
     *
     * @param name   Name(number) of the card
     * @param points Points that player gets upon capturing this card.
     */
    Cards(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * Gets the points
     *
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Overriden to string method
     *
     * @return Name(Number) of this card.
     */
    @Override
    public String toString() {
        return name;
    }
}
