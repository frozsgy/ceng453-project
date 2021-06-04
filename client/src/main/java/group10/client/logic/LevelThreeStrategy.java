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
 * Defines behavior of AI for third level.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class LevelThreeStrategy extends AiStrategy {


    /**
     * Overridden method to define first level behavior.
     * If there is only one card in middle, with 33% probability it bluffs.
     * If bluff, with 50% chance, it tries to make this a real bluff. If a real bluff is not possible, it selects a random card.
     * If real bluff possible, selects the matching card.
     * If does not bluff, tries all the cards at his hand calculates their scores. Selects the best card.
     * If all cards performs 0 on tests, selects a random card.
     * Removes the played card from its list.
     *
     * @param cardMappings Cards and their visual Rectangle representatives.
     * @return Pair of Played card and it is visual Rectangle representation.
     */
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        if (this.middle.size() == 1) {
            Card cardOnTop = this.middle.peek();
            Random rand = new Random();
            double bluff = Math.random();
            double bluffProb = 0.33;
            double whenGreaterThan = 1 - bluffProb;
            if (bluff > whenGreaterThan && !hasBluffed) { // do with 0.33 prob.
                hasBluffed = true;
                int headTail = rand.nextInt(2);
                if (headTail == 0) {  // do with 50% prob
                    // check if real pisti
                    for (Card tested : cards) {
                        if (tested.getCard() == cardOnTop.getCard()) {
                            Rectangle r = GameLogic.getRectangleByCard(cardMappings, tested);
                            this.removePlayedCard(tested);
                            return new Pair<>(r, tested);
                        }
                    }
                }
                int randomIndex = rand.nextInt(cards.size());
                Card c = cards.get(randomIndex);
                Rectangle r = GameLogic.getRectangleByCard(cardMappings, c);
                this.removePlayedCard(c);
                return new Pair<>(r, c);
            } else {
                hasBluffed = false;
                // check if pisti is possible first
                for (Card tested : cards) {
                    if (tested.getCard() == cardOnTop.getCard()) {
                        Rectangle r = GameLogic.getRectangleByCard(cardMappings, tested);
                        this.removePlayedCard(tested);
                        return new Pair<>(r, tested);
                    }
                }
            }
        }
        hasBluffed = false;
        Stack<Card> originalMiddle = (Stack<Card>) this.middle.clone();
        int score = 0;
        Card bestCard = null;
        for (Card tested : cards) {
            try {
                if (tested.getCard() == originalMiddle.peek().getCard() || tested.getCard() == Cards.JACK) {
                    GameLogic.getInstance().setMiddle((Stack<Card>) originalMiddle.clone());
                    GameLogic.getInstance().getMiddle().add(tested);
                    int testScore = GameLogic.getInstance().calculateStackScore();
                    if (testScore > score) {
                        score = testScore;
                        bestCard = tested;
                    }
                }
            } catch (Exception exception) {
                break;
            }
        }
        GameLogic.getInstance().setMiddle((Stack<Card>) originalMiddle.clone());
        this.middle = GameLogic.getInstance().getMiddle();
        if (bestCard != null) {
            Rectangle r = GameLogic.getRectangleByCard(cardMappings, bestCard);
            this.removePlayedCard(bestCard);
            return new Pair<>(r, bestCard);
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(cards.size());
        bestCard = cards.get(randomIndex);
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, bestCard);
        this.removePlayedCard(bestCard);
        return new Pair<>(r, bestCard);
    }

    /**
     * @param playerCards map of player cards
     * @param middle      middle stack
     * @see AiStrategy#AiStrategy(Map, Stack)
     */
    public LevelThreeStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
