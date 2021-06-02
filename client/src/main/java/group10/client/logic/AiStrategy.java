package group10.client.logic;

import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public abstract class AiStrategy {
    protected Map<PlayerEnum, List<Card>> playerCards;
    public abstract Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings);
    public AiStrategy(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
    }
}
