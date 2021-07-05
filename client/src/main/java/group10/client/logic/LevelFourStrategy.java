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

public class LevelFourStrategy extends AiStrategy{
    @Override
    public Pair<Rectangle, Card> playAsOpponent(Map<Rectangle, Card> cardMappings) {

        GameState gameState = (GameState) GameController._instance.getSocketServer().readSocket();
        GameLogic.getInstance().setCurrentState(gameState);
        Card thrown = gameState.getCardThrown();
        Rectangle r = GameLogic.getRectangleByCard(cardMappings, thrown);
        return new Pair<>(r, thrown);
    }

    public LevelFourStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}
