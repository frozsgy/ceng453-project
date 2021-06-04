package group10.client.logic;


import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameLogicTests {


    @Test
    @DisplayName("Score Calculation - JACKS")
    @Order(1)
    void scoreTestJack() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.add(new Card(Cards.JACK, Suits.DIAMOND));
        middle.add(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.add(new Card(Cards.JACK, Suits.CLUB));
        GameLogic instance = GameLogic.getInstance();
        int scores = instance.calculateStackScore();
        assertEquals(scores, 2);
    }

    @Test
    @DisplayName("Score Calculation - ACES")
    @Order(2)
    void scoreTestAce() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.push(new Card(Cards.ACE, Suits.CLUB));
        GameLogic instance = GameLogic.getInstance();
        int scores = instance.calculateStackScore();
        assertEquals(scores, 2);
    }

    @Test
    @DisplayName("Score Calculation - Two of Clubs")
    @Order(3)
    void scoreTestTwoOfClubs() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.push(new Card(Cards.TWO, Suits.CLUB));
        GameLogic instance = GameLogic.getInstance();
        int scores = instance.calculateStackScore();
        assertEquals(scores, 3);
    }

    @Test
    @DisplayName("Score Calculation - Ten of Diamonds")
    @Order(4)
    void scoreTestTenOfDiamonds() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.TEN, Suits.DIAMOND));
        middle.push(new Card(Cards.TWO, Suits.CLUB));
        GameLogic instance = GameLogic.getInstance();
        int scores = instance.calculateStackScore();
        assertEquals(scores, 6);
    }


    @Test
    @DisplayName("Give Middle Stack Cards to Last Winner ")
    @Order(5)
    void giveMiddleStackTest() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        GameLogic.getInstance().setLastWinner(PlayerEnum.TWO);
        GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
        Map<PlayerEnum, Integer> scores = GameLogic.getInstance().getScores();
        scores.replace(PlayerEnum.ONE, 15);
        scores.replace(PlayerEnum.TWO, 22);
        GameLogic.getInstance().setScores(scores);
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.TEN, Suits.DIAMOND));
        middle.push(new Card(Cards.TWO, Suits.CLUB));
        GameLogic.getInstance().giveMidStackCardsToLastWinner();
        scores = GameLogic.getInstance().getScores();
        assertEquals(scores.get(PlayerEnum.ONE), 15);
        assertEquals(scores.get(PlayerEnum.TWO), 22 + 3 + 6);
    }

    @Test
    @DisplayName("Give No Points for Equal Number of Cards ")
    @Order(6)
    void equalNumberOfCards() {
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        GameLogic.getInstance().setLastWinner(PlayerEnum.TWO);
        GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
        Map<PlayerEnum, Integer> scores = GameLogic.getInstance().getScores();
        scores.replace(PlayerEnum.ONE, 15);
        scores.replace(PlayerEnum.TWO, 22);
        GameLogic.getInstance().setScores(scores);
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.TEN, Suits.DIAMOND));
        middle.push(new Card(Cards.TWO, Suits.CLUB));
        List<Card> playerCardsList = GameLogic.getInstance().getPlayerCards().get(PlayerEnum.ONE);
        playerCardsList.add(new Card(Cards.FIVE, Suits.DIAMOND));
        playerCardsList.add(new Card(Cards.FOUR, Suits.DIAMOND));
        playerCardsList.add(new Card(Cards.SIX, Suits.DIAMOND));
        Map<PlayerEnum, List<Card>> playerCards = GameLogic.getInstance().getPlayerCards();
        playerCards.replace(PlayerEnum.ONE, playerCardsList);
        Map<PlayerEnum, Integer> playerCardCounts = GameLogic.getInstance().getPlayerCardCounts();
        playerCardCounts.replace(PlayerEnum.ONE, 3);
        GameLogic.getInstance().setPlayerCards(playerCards);
        GameLogic.getInstance().giveMidStackCardsToLastWinner();
        scores = GameLogic.getInstance().getScores();
        assertEquals(scores.get(PlayerEnum.ONE), 15);
        assertEquals(scores.get(PlayerEnum.TWO), 22 + 6);
    }



}
