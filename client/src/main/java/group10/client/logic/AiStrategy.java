package group10.client.logic;

import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Abstract class for AI behavior.
 * Used to implement strategy pattern.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public abstract class AiStrategy {
    /**
     * List of player cards that players currently own.
     */
    protected Map<PlayerEnum, List<Card>> playerCards;
    /**
     * Stack of cards standing on middle.
     */
    protected Stack<Card> middle;
    /**
     * Flag if AI has bluffed
     */
    protected boolean hasBluffed;

    /**
     * Abstract method that defines the behavior of AI.
     * Used to implement strategy pattern.
     *
     * @param cardMappings Cards and their visual Rectangle representatives.
     * @return Pair of played card and its representative Rectangle.
     */
    public abstract Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings);

    /**
     * Constructor for AI strategy
     *
     * @param playerCards List of player cards that players currently own.
     * @param middle      Stack of cards standing on middle.
     */
    public AiStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        this.playerCards = playerCards;
        this.middle = middle;
        this.hasBluffed = false;
    }

    /**
     * Method to remove the played card from the cards that AI
     * currently owns.
     *
     * @param card Card to be removed
     */
    protected void removePlayedCard(Card card) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        cards.remove(card);
        this.playerCards.replace(PlayerEnum.TWO, cards);
    }

    /**
     * Gets if the AI has bluffed.
     *
     * @return hasBluffed
     */
    public boolean getHasBluffed() {
        return hasBluffed;
    }
}
