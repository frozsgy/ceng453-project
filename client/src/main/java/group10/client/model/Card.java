package group10.client.model;

import group10.client.enums.Cards;
import group10.client.enums.Suits;

import java.util.Objects;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Card class that represents a card object.
 */
public class Card {

    /**
     * Number of the card, such as K, J, Q, 1, 10, etc.
     *
     * @see Cards
     */
    private Cards card;
    /**
     * Suit of the card such as Diamond
     *
     * @see Suits
     */
    private Suits suit;

    /**
     * Constructor for card
     *
     * @param card Number of the card
     * @param suit Suit of the card.
     */
    public Card(Cards card, Suits suit) {
        this.card = card;
        this.suit = suit;
    }

    /**
     * Gets the number of the card
     *
     * @return Number of the card
     * @see Cards
     */
    public Cards getCard() {
        return card;
    }

    /**
     * Sets the number of the card
     *
     * @param card Card number to be set.
     * @see Cards
     */
    public void setCard(Cards card) {
        this.card = card;
    }

    /**
     * Gets the suit of this card
     *
     * @return Suit of this card
     * @see Suits
     */
    public Suits getSuit() {
        return suit;
    }

    /**
     * Sets the suit of this card
     *
     * @param suit Suit to be set
     * @see Suits
     */
    public void setSuit(Suits suit) {
        this.suit = suit;
    }

    /**
     * Overridden equals method.
     *
     * @param o Object to be tested
     * @return False if class of o is not type Card or suit and number of o does not match this object's number and suit.
     * True if object is this object or has same number and suit.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card1 = (Card) o;
        return card == card1.card && suit == card1.suit;
    }

    /**
     * Overridden hashcode method.
     *
     * @return Hashcode of this object with card number and suit.
     */
    @Override
    public int hashCode() {
        return Objects.hash(card, suit);
    }

    /**
     * Overridden to string method.
     *
     * @return This card in string form with format number-suit
     */
    @Override
    public String toString() {
        return card + " - " + suit;
    }
}
