package group10.client.entity;

import group10.client.enums.PlayerEnum;
import group10.client.logic.GameLogic;
import group10.client.model.Card;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

public class GameState implements Serializable {

    private Stack<Card> middle;
    private PlayerEnum lastWinner;
    private Map<PlayerEnum, Integer> scores;
    private Map<PlayerEnum, List<Card>> playerCards;
    private PlayerEnum currentPlayer;
    private Map<PlayerEnum, Integer> playerCardCounts;
    private String hostPlayerName;
    private boolean bluffed;
    private Card cardThrown;

    private <T> Map<PlayerEnum,T> swapMapKeys(Map<PlayerEnum, T> map) {
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
    public GameState(GameLogic gameLogicVariables, boolean bluffed, Card cardThrown) {
        this.middle = gameLogicVariables.getMiddle();
        this.lastWinner = gameLogicVariables.getLastWinner();
        this.scores = new HashMap<>();
        scores.put(PlayerEnum.ONE, gameLogicVariables.getScores().get(PlayerEnum.TWO));
        scores.put(PlayerEnum.TWO, gameLogicVariables.getScores().get(PlayerEnum.ONE));
        this.playerCards = this.swapMapKeys(gameLogicVariables.getPlayerCards());
        this.currentPlayer = gameLogicVariables.getCurrentPlayer() == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
        this.playerCardCounts = this.swapMapKeys(gameLogicVariables.getPlayerCardCounts());
        this.hostPlayerName =  gameLogicVariables.getPlayerGameEntity().getPlayer().getUsername();
        this.bluffed = bluffed;
        this.cardThrown = cardThrown;
    }

    public Stack<Card> getMiddle() {
        return middle;
    }

    public void setMiddle(Stack<Card> middle) {
        this.middle = middle;
    }

    public PlayerEnum getLastWinner() {
        return lastWinner;
    }

    public void setLastWinner(PlayerEnum lastWinner) {
        this.lastWinner = lastWinner;
    }

    public Map<PlayerEnum, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<PlayerEnum, Integer> scores) {
        this.scores = scores;
    }

    public Map<PlayerEnum, List<Card>> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
    }

    public PlayerEnum getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerEnum currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getHostPlayerName() {
        return hostPlayerName;
    }

    public void setHostPlayerName(String hostPlayerName) {
        this.hostPlayerName = hostPlayerName;
    }

    public Map<PlayerEnum, Integer> getPlayerCardCounts() {
        return playerCardCounts;
    }

    public void setPlayerCardCounts(Map<PlayerEnum, Integer> playerCardCounts) {
        this.playerCardCounts = playerCardCounts;
    }
}
