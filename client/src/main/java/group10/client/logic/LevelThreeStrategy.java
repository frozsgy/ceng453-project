package group10.client.logic;

import group10.client.controller.GameController;
import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.*;

public class LevelThreeStrategy extends AiStrategy{
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        if (this.middle.size() == 1) {
            // check if pisti is possible first
            Card cardOnTop = this.middle.peek();
            for (Card tested : cards) {
                if (tested.getCard() == cardOnTop.getCard()) {
                    Rectangle r = GameLogic.getRectangleByCard(cardMappings, tested);
                    return new Pair<>(r, tested);
                }
            }
        }
        Stack<Card> originalMiddle = (Stack<Card>) this.middle.clone();
        int score = 0;
        Card bestCard = null;
        for (Card tested: cards) {
            try{
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
            return new Pair<>(r, bestCard);
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(cards.size());
        bestCard = cards.get(randomIndex);
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, bestCard);
        return new Pair(r, bestCard);
    }

    public LevelThreeStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
