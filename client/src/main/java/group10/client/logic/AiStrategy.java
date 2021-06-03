package group10.client.logic;

import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public abstract class AiStrategy {
    protected Map<PlayerEnum, List<Card>> playerCards;
    protected Stack<Card> middle;
    public abstract Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings);
    public AiStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        this.playerCards = playerCards;
        this.middle = middle;
    }
}
