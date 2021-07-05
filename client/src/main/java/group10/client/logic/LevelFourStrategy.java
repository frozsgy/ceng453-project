package group10.client.logic;

import group10.client.controller.GameController;
import group10.client.entity.GameState;
import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Concrete class that extends abstract AiStrategy
 * Defines behavior of AI for multiplayer level.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class LevelFourStrategy extends AiStrategy {

    /**
     * Plays according to the result of the socket request.
     *
     * @param cardMappings Cards and their visual Rectangle representatives.
     * @return thrown card and its rectangle as a pair.
     */
    @Override
    public Pair<Rectangle, Card> playAsOpponent(Map<Rectangle, Card> cardMappings) {

        GameState gameState = (GameState) GameController._instance.getSocketServer().readSocket();
        GameLogic.getInstance().setCurrentState(gameState);
        Card thrown = gameState.getCardThrown();
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, thrown);
        this.removePlayedCard(thrown);
        return new Pair<>(r, thrown);
    }

    /**
     * @param playerCards map of player cards
     * @param middle      middle stack
     * @see AiStrategy#AiStrategy(Map, Stack)
     */
    public LevelFourStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
