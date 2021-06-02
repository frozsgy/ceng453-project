package group10.client.model;

import group10.client.enums.Cards;
import group10.client.enums.Suits;

import java.util.Objects;

public class Card {

    private Cards card;
    private Suits suit;

    public Card(Cards card, Suits suit) {
        this.card = card;
        this.suit = suit;
    }

    public Cards getCard() {
        return card;
    }

    public void setCard(Cards card) {
        this.card = card;
    }

    public Suits getSuit() {
        return suit;
    }

    public void setSuit(Suits suit) {
        this.suit = suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card1 = (Card) o;
        return card == card1.card && suit == card1.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(card, suit);
    }
}
