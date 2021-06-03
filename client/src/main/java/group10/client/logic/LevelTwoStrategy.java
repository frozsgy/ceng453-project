package group10.client.logic;

import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.*;

public class LevelTwoStrategy extends AiStrategy{
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        if (this.middle.size() > 0) {
            Card cardOnTop = this.middle.get(this.middle.size() - 1);
            for (Card tested : cards) {
                if (tested.getCard() == cardOnTop.getCard() || tested.getCard() == Cards.JACK) {
                    Rectangle r = GameLogic.getRectangleByCard(cardMappings, tested);
                    return new Pair(r, tested);
                }
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(cards.size());
        Card card = cards.get(randIndex);
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, card);
        return new Pair(r, card);
    }

    public LevelTwoStrategy(Map<PlayerEnum, List<Card>> playerCards, ArrayList<Card> middle) {
        super(playerCards, middle);
    }
}
