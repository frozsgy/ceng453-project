package group10.client.logic;

import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Concrete class that extends abstract AiStrategy
 * Defines behavior of AI for first level.
 */
public class LevelOneStrategy extends AiStrategy {

    /**
     * Overridden method to define first level behavior.
     * As it is first level, it behaves very simple.
     * Throws card in order.
     * Removes the played card from its list.
     *
     * @param cardMappings Cards and their visual Rectangle representatives.
     * @return Pair of Played card and it is visual Rectangle representation.
     */
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        Card card = cards.get(0);
        this.removePlayedCard(card);
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, card);
        Pair<Rectangle, Card> pair = new Pair<>(r, card);
        return pair;
    }

    /**
     * @see AiStrategy#AiStrategy(Map, Stack)
     */
    public LevelOneStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
