package group10.client.entity;

import group10.client.enums.PlayerEnum;
import group10.client.logic.GameLogic;
import group10.client.model.Card;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that is used to transfer Game State between players
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * @see Serializable
 */
public class GameState implements Serializable {

    /**
     * Stack of cards in the middle stack
     */
    private Stack<Card> middle;
    /**
     * Last winner of the game
     */
    private PlayerEnum lastWinner;
    /**
     * Map of players and scores
     */
    private Map<PlayerEnum, Integer> scores;
    /**
     * Map of players and list of cards
     */
    private Map<PlayerEnum, List<Card>> playerCards;
    /**
     * Map of player card counts
     */
    private Map<PlayerEnum, Integer> playerCardCounts;
    /**
     * Host player name
     */
    private String hostPlayerName;
    /**
     * Flag to keep bluffs
     */
    private boolean bluffed;
    /**
     * Thrown card by player
     */
    private Card cardThrown;

    /**
     * Boolean to show if game is over or not.
     */
    private boolean isGameOver;

    /**
     * Swaps players of the given map. Used while fetching and syncing states.
     *
     * @param map Map to swap keys of
     * @param <T> Class that is stored in the map
     * @return Swapped map
     */
    private <T> Map<PlayerEnum, T> swapMapKeys(Map<PlayerEnum, T> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                key -> {
                    if (key.getKey() == PlayerEnum.ONE) {
                        return PlayerEnum.TWO;
                    } else if (key.getKey() == PlayerEnum.TWO) {
                        return PlayerEnum.ONE;
                    }
                    return PlayerEnum.NULL;
                }, Map.Entry::getValue)); // deep copy
    }

    // TODO
    // since we will pass this data between clients, we may also need to hold if the posting player had bluffed

    /**
     * Constructor
     *
     * @param gameLogicVariables game logic variables
     * @param bluffed            flag of if the user has bluffed or not
     * @param cardThrown         thrown card by player
     * @param isGameOver         denotes if game is ended or not.
     */
    public GameState(GameLogic gameLogicVariables, boolean bluffed, Card cardThrown, boolean isGameOver) {
        this.middle = new Stack<>();
        Stack<Card> middle = gameLogicVariables.getMiddle();
        this.middle.addAll(middle);
        this.lastWinner = gameLogicVariables.getLastWinner();
        this.scores = new HashMap<>();
        scores.put(PlayerEnum.ONE, gameLogicVariables.getScores().get(PlayerEnum.TWO));
        scores.put(PlayerEnum.TWO, gameLogicVariables.getScores().get(PlayerEnum.ONE));
        this.playerCards = new HashMap<>();
        Map<PlayerEnum, List<Card>> playerCards = this.swapMapKeys(gameLogicVariables.getPlayerCards());
        List<Card> p1 = new ArrayList(playerCards.get(PlayerEnum.ONE));
        List<Card> p2 = new ArrayList(playerCards.get(PlayerEnum.TWO));

        this.playerCards.put(PlayerEnum.ONE, p1);
        this.playerCards.put(PlayerEnum.TWO, p2);
        this.playerCardCounts = this.swapMapKeys(gameLogicVariables.getPlayerCardCounts());
        this.hostPlayerName = gameLogicVariables.getPlayerGameEntity().getPlayer().getUsername();
        this.bluffed = bluffed;
        this.cardThrown = cardThrown;
        this.isGameOver = isGameOver;
    }

    /**
     * Gets middle stack.
     *
     * @return Stack of cards
     */
    public Stack<Card> getMiddle() {
        return middle;
    }

    /**
     * Sets middle stack
     *
     * @param middle middle stack
     */
    public void setMiddle(Stack<Card> middle) {
        this.middle = middle;
    }

    /**
     * Gets last winner
     *
     * @return last winner
     */
    public PlayerEnum getLastWinner() {
        return lastWinner;
    }

    /**
     * Sets last winner
     *
     * @param lastWinner last winner value to be set
     */
    public void setLastWinner(PlayerEnum lastWinner) {
        this.lastWinner = lastWinner;
    }

    /**
     * Gets scores
     *
     * @return map of players and scores
     */
    public Map<PlayerEnum, Integer> getScores() {
        return scores;
    }

    /**
     * Sets scores
     *
     * @param scores new scores to be set
     */
    public void setScores(Map<PlayerEnum, Integer> scores) {
        this.scores = scores;
    }

    /**
     * Gets player cards
     *
     * @return player cards
     */
    public Map<PlayerEnum, List<Card>> getPlayerCards() {
        return playerCards;
    }

    /**
     * Sets player cards
     *
     * @param playerCards new map of player cards
     */
    public void setPlayerCards(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
    }

    /**
     * Gets host player name
     *
     * @return host player name
     */
    public String getHostPlayerName() {
        return hostPlayerName;
    }

    /**
     * Sets host player name
     *
     * @param hostPlayerName new host player name
     */
    public void setHostPlayerName(String hostPlayerName) {
        this.hostPlayerName = hostPlayerName;
    }

    /**
     * Gets player card counts
     *
     * @return map of player and card counts
     */
    public Map<PlayerEnum, Integer> getPlayerCardCounts() {
        return playerCardCounts;
    }

    /**
     * Sets player card counts
     *
     * @param playerCardCounts new value of player card counts
     */
    public void setPlayerCardCounts(Map<PlayerEnum, Integer> playerCardCounts) {
        this.playerCardCounts = playerCardCounts;
    }

    /**
     * Gets thrown card
     *
     * @return thrown card
     */
    public Card getCardThrown() {
        return cardThrown;
    }

    /**
     * Sets thrown card
     *
     * @param cardThrown new value of thrown card
     */
    public void setCardThrown(Card cardThrown) {
        this.cardThrown = cardThrown;
    }

    /**
     * Gets bluffed
     *
     * @return bluffed
     */
    public boolean isBluffed() {
        return bluffed;
    }

    /**
     * Sets bluffed
     *
     * @param bluffed new value of bluffed
     */
    public void setBluffed(boolean bluffed) {
        this.bluffed = bluffed;
    }

    /**
     * Gets the isGameEnded
     * @return Boolean that denotes if the game has ended or not.
     */
    public boolean isGameOver() {
        return isGameOver;
    }
}
