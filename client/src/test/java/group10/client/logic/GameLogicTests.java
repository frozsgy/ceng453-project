package group10.client.logic;


import group10.client.entity.GameState;
import group10.client.entity.Player;
import group10.client.entity.PlayerGame;
import group10.client.enums.Cards;
import group10.client.enums.MatchType;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Game Logic Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameLogicTests {

    private void resetGameLogic() {
        GameLogic.getInstance().resetFields();
        GameLogic.getInstance().resetScores();
    }

    @Test
    @DisplayName("Reset Fields")
    @Order(1)
    void resetFieldsTest() {
        this.resetGameLogic();
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.add(new Card(Cards.JACK, Suits.DIAMOND));
        middle.add(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.add(new Card(Cards.JACK, Suits.CLUB));
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
        playerCardsList = GameLogic.getInstance().getPlayerCards().get(PlayerEnum.TWO);
        playerCardsList.add(new Card(Cards.FIVE, Suits.SPADE));
        playerCardsList.add(new Card(Cards.FOUR, Suits.SPADE));
        playerCardsList.add(new Card(Cards.SIX, Suits.SPADE));
        playerCards.replace(PlayerEnum.TWO, playerCardsList);
        Map<PlayerEnum, Integer> playerCardCounts = GameLogic.getInstance().getPlayerCardCounts();
        playerCardCounts.replace(PlayerEnum.ONE, 3);
        GameLogic.getInstance().setPlayerCards(playerCards);
        GameLogic.getInstance().resetFields();
        assertTrue(GameLogic.getInstance().getMiddle().isEmpty());
        assertTrue(GameLogic.getInstance().getPlayerCards().get(PlayerEnum.ONE).isEmpty());
        assertTrue(GameLogic.getInstance().getPlayerCards().get(PlayerEnum.TWO).isEmpty());
        assertEquals(GameLogic.getInstance().getPlayerCardCounts().get(PlayerEnum.ONE), 0);
        assertEquals(GameLogic.getInstance().getPlayerCardCounts().get(PlayerEnum.TWO), 0);
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.ONE), 15);
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.TWO), 22);
    }

    @Test
    @DisplayName("Reset Scores")
    @Order(2)
    void resetScoresTest() {
        this.resetGameLogic();
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        middle.add(new Card(Cards.JACK, Suits.DIAMOND));
        middle.add(new Card(Cards.FIVE, Suits.DIAMOND));
        middle.add(new Card(Cards.JACK, Suits.CLUB));
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
        playerCardsList = GameLogic.getInstance().getPlayerCards().get(PlayerEnum.TWO);
        playerCardsList.add(new Card(Cards.FIVE, Suits.SPADE));
        playerCardsList.add(new Card(Cards.FOUR, Suits.SPADE));
        playerCardsList.add(new Card(Cards.SIX, Suits.SPADE));
        Map<PlayerEnum, List<Card>> playerCards = GameLogic.getInstance().getPlayerCards();
        playerCards.replace(PlayerEnum.TWO, playerCardsList);
        playerCards.replace(PlayerEnum.ONE, playerCardsList);
        Map<PlayerEnum, Integer> playerCardCounts = GameLogic.getInstance().getPlayerCardCounts();
        playerCardCounts.replace(PlayerEnum.ONE, 3);
        GameLogic.getInstance().setPlayerCards(playerCards);
        GameLogic.getInstance().resetScores();
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.ONE), 0);
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.TWO), 0);
        assertFalse(GameLogic.getInstance().getMiddle().isEmpty());
        assertFalse(GameLogic.getInstance().getPlayerCards().get(PlayerEnum.ONE).isEmpty());
        assertFalse(GameLogic.getInstance().getPlayerCards().get(PlayerEnum.TWO).isEmpty());
        assertEquals(GameLogic.getInstance().getPlayerCardCounts().get(PlayerEnum.ONE), 3);
        assertEquals(GameLogic.getInstance().getPlayerCardCounts().get(PlayerEnum.TWO), 0);

    }


    @Test
    @DisplayName("Score Calculation - JACKS")
    @Order(3)
    void scoreTestJack() {
        this.resetGameLogic();
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
    @Order(4)
    void scoreTestAce() {
        this.resetGameLogic();
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
    @Order(5)
    void scoreTestTwoOfClubs() {
        this.resetGameLogic();
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
    @Order(6)
    void scoreTestTenOfDiamonds() {
        this.resetGameLogic();
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
    @Order(7)
    void giveMiddleStackTest() {
        this.resetGameLogic();
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
    @DisplayName("Give No Bonus Points for Equal Number of Cards")
    @Order(8)
    void equalNumberOfCards() {
        this.resetGameLogic();
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

    @Test
    @DisplayName("Give Bonus Points for Different Number of Cards")
    @Order(9)
    void moreNumberOfCards() {
        this.resetGameLogic();
        Stack<Card> middle = GameLogic.getInstance().getMiddle();
        GameLogic.getInstance().setLastWinner(PlayerEnum.TWO);
        GameLogic.getInstance().setCurrentPlayer(PlayerEnum.TWO);
        Map<PlayerEnum, Integer> scores = GameLogic.getInstance().getScores();
        scores.replace(PlayerEnum.ONE, 15);
        scores.replace(PlayerEnum.TWO, 22);
        GameLogic.getInstance().setScores(scores);
        middle.push(new Card(Cards.ACE, Suits.DIAMOND));
        middle.push(new Card(Cards.TEN, Suits.DIAMOND));
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
        assertEquals(scores.get(PlayerEnum.ONE), 15 + 3);
        assertEquals(scores.get(PlayerEnum.TWO), 22 + 4);
    }

    @Test
    @DisplayName("Match Type Test - No Match")
    @Order(10)
    void matchTypeNo() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.TWO, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.FIVE, Suits.SPADE);
        MatchType matchType = GameLogic.getInstance().getMatchType(candidateCard, null);
        assertEquals(matchType, MatchType.NO);
    }

    @Test
    @DisplayName("Match Type Test - Regular")
    @Order(11)
    void matchTypeRegular() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.TWO, Suits.DIAMOND));
        GameLogic.getInstance().getMiddle().push(new Card(Cards.FIVE, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.FIVE, Suits.SPADE);
        MatchType matchType = GameLogic.getInstance().getMatchType(candidateCard, null);
        assertEquals(matchType, MatchType.REGULAR);
    }


    @Test
    @DisplayName("Match Type Test - Pisti")
    @Order(12)
    void matchTypePisti() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.TWO, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.TWO, Suits.SPADE);
        MatchType matchType = GameLogic.getInstance().getMatchType(candidateCard, null);
        assertEquals(matchType, MatchType.PISTI);
    }

    @Test
    @DisplayName("Match Type Test - Double Pisti")
    @Order(13)
    void matchTypeDoublePisti() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.JACK, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.JACK, Suits.SPADE);
        MatchType matchType = GameLogic.getInstance().getMatchType(candidateCard, null);
        assertEquals(matchType, MatchType.DOUBLE_PISTI);
    }

    @Test
    @DisplayName("Handle Throw Test - No Match")
    @Order(14)
    void throwNo() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.TWO, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.FIVE, Suits.SPADE);
        boolean handleThrow = GameLogic.getInstance().handleThrow(candidateCard, PlayerEnum.ONE, null);
        assertFalse(handleThrow);
    }

    @Test
    @DisplayName("Handle Throw Test - Match")
    @Order(15)
    void throwYes() {
        this.resetGameLogic();
        GameLogic.getInstance().getMiddle().push(new Card(Cards.TWO, Suits.DIAMOND));
        Card candidateCard = new Card(Cards.TWO, Suits.SPADE);
        boolean handleThrow = GameLogic.getInstance().handleThrow(candidateCard, PlayerEnum.ONE, null);
        assertTrue(handleThrow);
    }

    @Test
    @DisplayName("Add Score to Player")
    @Order(16)
    void addScoreToPlayerTest() {
        this.resetGameLogic();
        Map<PlayerEnum, Integer> scores = GameLogic.getInstance().getScores();
        scores.replace(PlayerEnum.ONE, 10);
        scores.replace(PlayerEnum.TWO, 12);
        GameLogic.getInstance().addScoreToPlayer(PlayerEnum.TWO, 30);
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.ONE), 10);
        assertEquals(GameLogic.getInstance().getScores().get(PlayerEnum.TWO), 42);
    }

    @Test
    @DisplayName("AI Strategy Test")
    @Order(17)
    void aiStrategyTest() {
        this.resetGameLogic();
        GameLogic.getInstance().setAiStrategy(1);
        AiStrategy aiStrategy = GameLogic.getInstance().getAiStrategy();
        assertTrue(aiStrategy instanceof LevelOneStrategy);
        GameLogic.getInstance().setAiStrategy(2);
        AiStrategy aiStrategyTwo = GameLogic.getInstance().getAiStrategy();
        assertTrue(aiStrategyTwo instanceof LevelTwoStrategy);
        GameLogic.getInstance().setAiStrategy(3);
        AiStrategy aiStrategyThree = GameLogic.getInstance().getAiStrategy();
        assertTrue(aiStrategyThree instanceof LevelThreeStrategy);
    }

    @Test
    @DisplayName("Multiplayer Strategy Test")
    @Order(18)
    void multiStrategyTest() {
        this.resetGameLogic();
        GameLogic.getInstance().setAiStrategy(4);
        AiStrategy aiStrategy = GameLogic.getInstance().getAiStrategy();
        assertTrue(aiStrategy instanceof LevelFourStrategy);
    }

    @Test
    @DisplayName("Multiplayer Protocol Serialization Test")
    @Order(19)
    void serializationTest()  {
        PlayerGame player = new PlayerGame();
        Player pl = new Player();
        pl.setUsername("asdsaa");
        player.setPlayer(pl);
        GameLogic.getInstance().setPlayerGameEntity(player);
        GameState state = new GameState(GameLogic.getInstance(), false, null, false);
        assertDoesNotThrow(() ->  new ObjectOutputStream(new ByteArrayOutputStream()).writeObject(state));
    }


}
