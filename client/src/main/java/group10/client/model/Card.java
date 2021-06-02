package group10.client.model;

import group10.client.enums.Cards;
import group10.client.enums.Suits;

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
}
