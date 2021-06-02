package group10.client.logic;

import group10.client.enums.PlayerEnum;
import group10.client.model.Card;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LevelOneStrategy extends AiStrategy{
    @Override
    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        Card card = cards.get(0);
        cards.remove(card);
        Rectangle r = null;
        for (Map.Entry<Rectangle, Card> entry: cardMappings.entrySet()) {
            if (card.equals(entry.getValue())) {
                r = entry.getKey();
            }
        }
//        r.setVisible(false);
        //midStack.getChildren().add(r.getParent());
        this.playerCards.replace(PlayerEnum.TWO, cards);
        Pair<Rectangle, Card> pair = new Pair<>(r, card);
        return pair;
    }
    public LevelOneStrategy(Map<PlayerEnum, List<Card>> playerCards, Stack<Card> middle) {
        super(playerCards, middle);
    }
}