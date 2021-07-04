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
    private PlayerGame hostPlayerEntity;
    private Map<PlayerEnum, Integer> playerCardCounts;

    private <T> Map<PlayerEnum,T> swapMapKeys(Map<PlayerEnum, T> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                key -> {
                    if (key.getKey() == PlayerEnum.ONE) {
                        return PlayerEnum.TWO;
                    }
                    return PlayerEnum.ONE;
                }, Map.Entry::getValue)); // deep copy
    }

    public GameState(GameLogic gameLogicVariables) {
        this.middle = gameLogicVariables.getMiddle();
        this.lastWinner = gameLogicVariables.getLastWinner();
        this.scores = new HashMap();
        scores.put(PlayerEnum.ONE, gameLogicVariables.getScores().get(PlayerEnum.TWO));
        scores.put(PlayerEnum.TWO, gameLogicVariables.getScores().get(PlayerEnum.ONE));
        this.playerCards = this.swapMapKeys(gameLogicVariables.getPlayerCards());
        this.currentPlayer = gameLogicVariables.getCurrentPlayer() == PlayerEnum.ONE ? PlayerEnum.TWO : PlayerEnum.ONE;
        this.hostPlayerEntity = gameLogicVariables.getPlayerGameEntity();
        this.playerCardCounts = this.swapMapKeys(gameLogicVariables.getPlayerCardCounts());
    }
}
