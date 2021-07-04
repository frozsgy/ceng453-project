package group10.client.logic;


import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Level Three Strategy Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LevelThreeStrategyTests {

    List<Card> cardList;

    private List<Card> initCards(boolean match) {
        GameLogic.getInstance().resetScores();
        GameLogic.getInstance().resetFields();
        this.cardList = new ArrayList<>();
        cardList.add(new Card(Cards.FIVE, Suits.DIAMOND));
        cardList.add(new Card(Cards.FOUR, Suits.DIAMOND));
        cardList.add(new Card(Cards.SIX, Suits.DIAMOND));
        cardList.add(new Card(Cards.SEVEN, Suits.DIAMOND));
        cardList.add(new Card(Cards.FIVE, Suits.SPADE));
        cardList.add(new Card(Cards.FOUR, Suits.SPADE));
        cardList.add(new Card(Cards.SIX, Suits.SPADE));
        cardList.add(new Card(Cards.SEVEN, Suits.SPADE));
        if (match) {
            cardList.add(new Card(Cards.SIX, Suits.CLUB));
        } else {
            cardList.add(new Card(Cards.NINE, Suits.CLUB));
        }
        return this.cardList;
    }

    private Map<Rectangle, Card> init(boolean match) {
        GameLogic.getInstance().resetFields();
        GameLogic.getInstance().resetScores();
        List<Card> cards = this.initCards(match);
        Map<PlayerEnum, List<Card>> playerCards = GameLogic.getInstance().getPlayerCards();
        List<Card> playerCardsList = playerCards.get(PlayerEnum.ONE);
        for (int i = 0; i < 4; i++) {
            playerCardsList.add(cards.get(i));
        }
        playerCards.replace(PlayerEnum.ONE, playerCardsList);
        playerCardsList = playerCards.get(PlayerEnum.TWO);
        for (int i = 4; i < 8; i++) {
            playerCardsList.add(cards.get(i));
        }
        playerCards.replace(PlayerEnum.TWO, playerCardsList);
        GameLogic.getInstance().setPlayerCards(playerCards);
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.push(cards.get(8));
        GameLogic.getInstance().setMiddle(middle);
        Map<Rectangle, Card> mockMap = new HashMap<>();
        for (Card c : cards) {
            mockMap.put(new Rectangle(), c);
        }
        return mockMap;
    }

    @Test
    @DisplayName("Play as Computer - Throw Matching Card")
    @Order(1)
    void playAsComputerTest() {
        Pair<Rectangle, Card> rectangleCardPair;
        while (true) {
            Map<Rectangle, Card> mockMap = this.init(true);
            LevelThreeStrategy strategy = new LevelThreeStrategy(GameLogic.getInstance().getPlayerCards(), GameLogic.getInstance().getMiddle());
            rectangleCardPair = strategy.playAsOpponent(mockMap);
            if (!strategy.getHasBluffed()) {
                break;
            }
        }
        assertEquals(rectangleCardPair.getValue(), this.cardList.get(6));

    }

    @Test
    @DisplayName("Play as Computer - Throw First Card")
    @Order(2)
    void playAsComputerNoMatchTest() {
        Pair<Rectangle, Card> rectangleCardPair;
        while (true) {
            Map<Rectangle, Card> mockMap = this.init(false);
            LevelThreeStrategy strategy = new LevelThreeStrategy(GameLogic.getInstance().getPlayerCards(), GameLogic.getInstance().getMiddle());
            rectangleCardPair = strategy.playAsOpponent(mockMap);
            if (!strategy.getHasBluffed()) {
                break;
            }
        }
        assert((rectangleCardPair.getValue() == this.cardList.get(4)) || (rectangleCardPair.getValue() == this.cardList.get(5)) || (rectangleCardPair.getValue() == this.cardList.get(6)) || (rectangleCardPair.getValue() == this.cardList.get(7)));
    }

    @Test
    @DisplayName("Play as Computer - Test Bluff")
    @Order(3)
    void bluffTest() {
        boolean bluffed = false;
        for (int i = 0; i < 100; i++) {
            Map<Rectangle, Card> mockMap = this.init(false);
            LevelThreeStrategy strategy = new LevelThreeStrategy(GameLogic.getInstance().getPlayerCards(), GameLogic.getInstance().getMiddle());
            Pair<Rectangle, Card> rectangleCardPair = strategy.playAsOpponent(mockMap);
            assert((rectangleCardPair.getValue() == this.cardList.get(4)) || (rectangleCardPair.getValue() == this.cardList.get(5)) || (rectangleCardPair.getValue() == this.cardList.get(6)) || (rectangleCardPair.getValue() == this.cardList.get(7)));
            bluffed = strategy.getHasBluffed();
            if (bluffed) {
                break;
            }
        }
        assertTrue(bluffed);


    }
}
