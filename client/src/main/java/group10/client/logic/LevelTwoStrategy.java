package group10.client.logic;

import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Concrete class that extends abstract AiStrategy
 * Defines behavior of AI for second level.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class LevelTwoStrategy extends AiStrategy {
    /**
     * Overridden method to define first level behavior.
     * It checks the middle stack. If middle stack is empty, it plays a random card.
     * If not empty, then checks the cards at its hands and tries to find a matching card.
     * If finds, throws it.
     * Otherwise, selects a random card.
     * Removes the played card from its list.
     *
     * @param cardMappings Cards and their visual Rectangle representatives.
     * @return Pair of Played card and it is visual Rectangle representation.
     */
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        if (this.middle.size() > 0) {
            Card cardOnTop = this.middle.peek();
            for (Card tested : cards) {
                if (tested.getCard() == cardOnTop.getCard() || tested.getCard() == Cards.JACK) {
                    Rectangle r = GameLogic.getRectangleByCard(cardMappings, tested);
                    this.removePlayedCard(tested);
                    return new Pair<>(r, tested);
                }
            }
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(cards.size());
        Card card = cards.get(randIndex);
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, card);
        this.removePlayedCard(card);
        return new Pair<>(r, card);
    }

    /**
     * @see AiStrategy#AiStrategy(Map, Stack)
     */
    public LevelTwoStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
