package group10.client.logic;

import group10.client.controller.GameController;
import group10.client.entity.GameState;
import group10.client.entity.Level;
import group10.client.entity.PlayerGame;
import group10.client.enums.Cards;
import group10.client.enums.MatchType;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import group10.client.service.HTTPService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static group10.client.constants.GameConstants.*;
import static group10.client.utility.UIUtility.logToScreen;

/**
 * Singleton class that manages game logic
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class GameLogic {

    /**
     * Logger to log messages
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLogic.class);
    /**
     * Stack for cards on middle.
     */
    private Stack<Card> middle;
    /**
     * Holds the player who got the last points.
     */
    private PlayerEnum lastWinner = PlayerEnum.NULL;
    /**
     * Holds the scores by players.
     */
    private Map<PlayerEnum, Integer> scores;
    /**
     * Holds the current cards by players.
     */
    private Map<PlayerEnum, List<Card>> playerCards;
    /**
     * Holds who currently is playing.
     */
    private PlayerEnum currentPlayer = PlayerEnum.ONE;
    /**
     * Entity representation of the player.
     */
    private PlayerGame playerGameEntity;
    /**
     * AI's currently assigned strategy.
     * Uses strategy pattern.
     */
    private AiStrategy strategy;
    /**
     * Holds how many cards a player captured so far.
     */
    private Map<PlayerEnum, Integer> playerCardCounts;
    /**
     * Singleton instance.
     */
    private static GameLogic instance;

    /**
     * Game state to keep current state
     */
    private GameState currentState;

    /**
     * Gets current state
     *
     * @return current state
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Sets curernt state
     *
     * @param currentState new value of current state
     */
    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    /**
     * Creates a new instance if it does not exist and assigns it to instance field
     *
     * @return Singleton instance
     */
    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    /**
     * Resets middle, all player cards, and all player card counts.
     * Called on when cleaning up for the next level.
     */
    public void resetFields() {
        this.middle = new Stack<>();
        this.playerCards = new HashMap<>();
        this.playerCards.put(PlayerEnum.ONE, new ArrayList<>());
        this.playerCards.put(PlayerEnum.TWO, new ArrayList<>());
        this.playerCardCounts = new HashMap<>();
        this.playerCardCounts.put(PlayerEnum.ONE, 0);
        this.playerCardCounts.put(PlayerEnum.TWO, 0);
    }

    /**
     * Resets the scores of AI and player.
     */
    public void resetScores() {
        this.scores = new HashMap<>();
        this.scores.put(PlayerEnum.ONE, 0);
        this.scores.put(PlayerEnum.TWO, 0);
    }

    /**
     * Private constructor for game logic class.
     * Resets everything and sets ai strategy to LevelOneStrategy
     */
    private GameLogic() {
        this.resetScores();
        this.resetFields();
        this.setAiStrategy(1);
    }

    /**
     * Reads game logic from GameState instance and updates accordingly
     *
     * @param state game state
     */
    public void readLogicFromState(GameState state) {
        this.middle = state.getMiddle();
        this.lastWinner = state.getLastWinner();
        this.scores = state.getScores();
        this.playerCards = state.getPlayerCards();
//        this.currentPlayer = state.getCurrentPlayer();
        this.playerCardCounts = state.getPlayerCardCounts();
        // TODO
        // this currently syncs the initial player cards.
        // we need to do it for every stage, not just for initial.
        GameController._instance.bulkAddToMiddle(this.middle);
        GameController._instance.bulkScoreUpdate();
        if (this.playerCards.get(PlayerEnum.ONE).size() == CARD_PER_HAND) {
            System.out.println(this.playerCards.get(PlayerEnum.ONE));
            System.out.println("============REINIT================");
            GameController._instance.initPlayerCards(true);
            GameController._instance.initOpponentCards();
            // update players self view.
        }
    }

    public void waitForHost() {
        GameState state = (GameState) GameController._instance.getSocketClient().readSocket();
        Platform.runLater(() -> {
            readLogicFromState(state);
            GameController._instance.removeOneCardFromOpponent();
            this.currentPlayer = PlayerEnum.TWO;
        });
    }
    public void startWaitForHostTask() {
        Task<Boolean> idleTask = new Task<>() {
            @Override
            public Boolean call() {
                GameLogic.getInstance().waitForHost();
                return true;
            }
        };
        idleTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                (EventHandler<WorkerStateEvent>) t -> {
                    LOGGER.info("read the state");
                });
        new Thread(idleTask).start();
    }
    /**
     * Sets ai strategy for strategy pattern
     *
     * @param level Current level that AI must behave accordingly.
     */
    public void setAiStrategy(int level) {
        if (level == 1) {
            strategy = new LevelOneStrategy(this.playerCards, this.middle);
        } else if (level == 2) {
            strategy = new LevelTwoStrategy(this.playerCards, this.middle);
        } else if (level == 3) {
            strategy = new LevelThreeStrategy(this.playerCards, this.middle);
        } else {
            strategy = new LevelFourStrategy(this.playerCards, this.middle);
        }
    }

    /**
     * Gets all players' cards
     *
     * @return Player and AI cards
     */
    public Map<PlayerEnum, List<Card>> getPlayerCards() {
        return playerCards;
    }

    /**
     * Sets player cards
     *
     * @param playerCards Cards to be set
     */
    public void setPlayerCards(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
    }

    /**
     * Gets cards on middle
     *
     * @return middle
     */
    public Stack<Card> getMiddle() {
        return middle;
    }

    /**
     * Sets middle
     *
     * @param middle Middle to be set
     */
    public void setMiddle(Stack<Card> middle) {
        this.middle = middle;
    }

    /**
     * Gets last winner
     *
     * @return Player who won the last score
     */
    public PlayerEnum getLastWinner() {
        return lastWinner;
    }

    /**
     * Sets the last winner
     *
     * @param lastWinner Winner to be set
     */
    public void setLastWinner(PlayerEnum lastWinner) {
        this.lastWinner = lastWinner;
    }

    /**
     * Gets scores
     *
     * @return Scores
     */
    public Map<PlayerEnum, Integer> getScores() {
        return scores;
    }

    /**
     * Sets scores
     *
     * @param scores Scores to be set
     */
    public void setScores(Map<PlayerEnum, Integer> scores) {
        this.scores = scores;
    }

    /**
     * Gets player that has the turn.
     *
     * @return Current playing player
     */
    public PlayerEnum getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets current player
     *
     * @param currentPlayer Player to be set
     */
    public void setCurrentPlayer(PlayerEnum currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets player entity
     *
     * @return Player game entity
     */
    public PlayerGame getPlayerGameEntity() {
        return playerGameEntity;
    }

    /**
     * Sets player game entity
     *
     * @param playerGameEntity Entity to be set
     */
    public void setPlayerGameEntity(PlayerGame playerGameEntity) {
        this.playerGameEntity = playerGameEntity;
    }

    /**
     * Gets AI strategy
     *
     * @return AI's currently assigned strategy
     */
    public AiStrategy getAiStrategy() {
        return this.strategy;
    }

    /**
     * Gets player card counts
     *
     * @return All players' card counts.
     */
    public Map<PlayerEnum, Integer> getPlayerCardCounts() {
        return playerCardCounts;
    }

    /**
     * Sets player card counts.
     *
     * @param playerCardCounts Counts to be set
     */
    public void setPlayerCardCounts(Map<PlayerEnum, Integer> playerCardCounts) {
        this.playerCardCounts = playerCardCounts;
    }

    /**
     * Adds score to player
     *
     * @param player Player that score will be added to
     * @param score  Score to be added
     */
    public void addScoreToPlayer(PlayerEnum player, int score) {
        this.lastWinner = player;
        Integer currentScore = this.scores.get(player);
        this.scores.replace(player, currentScore + score);
    }

    /**
     * Given a card, checks the card on top of middle and returns the match type with the tested card.
     * Logs card placement to a text area on screen and to console.
     *
     * @param candidateCard Card to be tested against card on top of mid
     * @param logArea       Text area that messages are logged to
     * @return Match type of the test.
     * @see MatchType
     */
    public MatchType getMatchType(Card candidateCard, TextArea logArea) {
        if (this.middle.isEmpty()) {
            return MatchType.NO;
        }
        Card topCard = this.middle.peek();
        logToScreen("Placed " + candidateCard + " on top of " + topCard, logArea, LOGGER);
        if (this.middle.size() == 1 && candidateCard.getCard().equals(topCard.getCard())) {
            if (candidateCard.getCard() == Cards.JACK) {
                return MatchType.DOUBLE_PISTI;
            } else {
                return MatchType.PISTI;
            }
        } else if (candidateCard.getCard().equals(topCard.getCard()) || candidateCard.getCard() == Cards.JACK) {
            return MatchType.REGULAR;
        } else {
            return MatchType.NO;
        }
    }

    /**
     * Checks match type of the thrown card and adds score to player accordingly.
     * Logs it operation to text area of screen and to console.
     *
     * @param candidateCard Card that was thrown and to be tested
     * @param player        Player who has thrown the card
     * @param logArea       Text area that log is printed to
     * @return True if there was a match. False otherwise.
     */
    public boolean handleThrow(Card candidateCard, PlayerEnum player, TextArea logArea) {
        MatchType matchType = this.getMatchType(candidateCard, logArea);
        this.middle.push(candidateCard);
        if (matchType != MatchType.NO) {
            this.lastWinner = player;
            int stackScore = this.calculateStackScore();
            if (matchType == MatchType.REGULAR) {
                this.addScoreToPlayer(player, stackScore);
            } else if (matchType == MatchType.PISTI) {
                logToScreen("Player " + player + " scored a Pisti!", logArea, LOGGER);
                this.addScoreToPlayer(player, stackScore + PISTI);
            } else {
                logToScreen("Player " + player + " scored a Double Pisti!", logArea, LOGGER);
                this.addScoreToPlayer(player, DOUBLE_PISTI);
            }
            logToScreen("Score for " + player + ": " + this.scores.get(player), logArea, LOGGER);
            playerCardCounts.replace(player, playerCardCounts.get(player) + this.middle.size());
            this.middle.clear();
            return true;
        }
        return false;
    }

    /**
     * Calculates and returns the score of the middle stack.
     * Also clears middle stack while doing so.
     *
     * @return Score of middle stack.
     */
    public int calculateStackScore() {
        // LevelThreeStrategy uses this method, be careful when changing it.
        // calculate score of the stack, in case of match
        // each jack - 1
        // each ace -- 1
        // two of clubs -- 2
        // ten of diamonds -- 3
        // most cards -- 3 :: equality -> no team -- done at mid stack
        // pisti -- 10
        // double pisti -- 20
        int score = 0;
        while (!this.middle.isEmpty()) {
            Card pop = this.middle.pop();
            Cards card = pop.getCard();
            Suits suit = pop.getSuit();
            if (card == Cards.JACK || card == Cards.ACE) {
                score++;
            } else if (card == Cards.TWO && suit == Suits.CLUB) {
                score += 2;
            } else if (card == Cards.TEN && suit == Suits.DIAMOND) {
                score += 3;
            }
        }
        return score;
    }

    /**
     * Returns if second player's (AI) hand empty.
     *
     * @return True if opponent has no cards. False otherwise.
     */
    public boolean isHandEmpty() {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        return cards.isEmpty();
    }

    /**
     * Given the card, returns the rectangle that maps to it.
     *
     * @param cardMappings Card mapping that contains all Card-Rectangle mappings.
     * @param card         Card to be searched
     * @return null if matching rectangle not found. Found rectangle otherwise.
     */
    public static Rectangle getRectangleByCard(Map<Rectangle, Card> cardMappings, Card card) {
        Rectangle r = null;
        for (Map.Entry<Rectangle, Card> entry : cardMappings.entrySet()) {
            if (card.equals(entry.getValue())) {
                r = entry.getKey();
            }
        }
        return r;
    }

    /**
     * Upon ending of a level, gives the remaining card on the middle to player
     * who won the last scores. Clears middle and adds score to corresponding player.
     */
    public void giveMidStackCardsToLastWinner() {
        int stackSize = this.middle.size();
        int stackScore = this.calculateStackScore();
        List<Card> cards = this.playerCards.get(this.lastWinner);
        this.playerCardCounts.replace(this.lastWinner, this.playerCardCounts.get(this.lastWinner) + stackSize);
        while (!this.middle.isEmpty()) {
            Card pop = this.middle.pop();
            cards.add(pop);
        }
        this.playerCards.replace(this.lastWinner, cards);
        this.scores.replace(this.lastWinner, this.scores.get(this.lastWinner) + stackScore);
        if (this.playerCardCounts.get(PlayerEnum.ONE) > this.playerCardCounts.get(PlayerEnum.TWO)) {
            this.scores.replace(PlayerEnum.ONE, this.scores.get(PlayerEnum.ONE) + 3);
        } else if (this.playerCardCounts.get(PlayerEnum.ONE) < this.playerCardCounts.get(PlayerEnum.TWO)) {
            this.scores.replace(PlayerEnum.TWO, this.scores.get(PlayerEnum.TWO) + 3);
        }

    }

    /**
     * POSTs the scores to server via HTTP.
     *
     * @param logArea log area to print logs
     */
    public void sendScores(TextArea logArea) {
        long gameId = this.playerGameEntity.getGame().getId();
        long myScore = this.scores.get(PlayerEnum.ONE);
        long opponentScore = this.scores.get(PlayerEnum.TWO);
        logToScreen("---- Round Ended ----", logArea, LOGGER);
        logToScreen("Player score: " + myScore, logArea, LOGGER);
        logToScreen("Opponent score: " + opponentScore, logArea, LOGGER);
        if (myScore >= MAX_SCORE) {
            long levelScore = myScore - opponentScore;
            HTTPService.getInstance().sendScores(new Level(gameId, levelScore));
        }
    }
}
