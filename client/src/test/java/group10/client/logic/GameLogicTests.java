package group10.client.logic;


import group10.client.enums.Cards;
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
        middle.push(new Card(Cards.JACK, Suits.DIAMOND));
        middle.push(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.push(new Card(Cards.JACK, Suits.CLUB));
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
    @DisplayName("Score Calculation - Two of Clubs")
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



}
