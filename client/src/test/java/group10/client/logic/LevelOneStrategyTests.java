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

@SpringBootTest
@DisplayName("Level One Strategy Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LevelOneStrategyTests {

    List<Card> cardList;

    private List<Card> initCards() {
        this.cardList = new ArrayList<>();
        cardList.add(new Card(Cards.FIVE, Suits.DIAMOND));
        cardList.add(new Card(Cards.FOUR, Suits.DIAMOND));
        cardList.add(new Card(Cards.SIX, Suits.DIAMOND));
        cardList.add(new Card(Cards.SEVEN, Suits.DIAMOND));
        cardList.add(new Card(Cards.FIVE, Suits.SPADE));
        cardList.add(new Card(Cards.FOUR, Suits.SPADE));
        cardList.add(new Card(Cards.SIX, Suits.SPADE));
        cardList.add(new Card(Cards.SEVEN, Suits.SPADE));
        cardList.add(new Card(Cards.FIVE, Suits.CLUB));
        return this.cardList;
    }

    private Map<Rectangle, Card> init() {
        final List<Card> cards = this.initCards();
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
    @DisplayName("Play as Computer - Throw First Card")
    @Order(1)
    void playAsComputerTest() {
        Map<Rectangle, Card> mockMap = this.init();
        LevelOneStrategy strategy = new LevelOneStrategy(GameLogic.getInstance().getPlayerCards(), GameLogic.getInstance().getMiddle());
        Pair<Rectangle, Card> rectangleCardPair = strategy.playAsComputer(mockMap);
        assertEquals(rectangleCardPair.getValue(), this.cardList.get(4));

    }
}
