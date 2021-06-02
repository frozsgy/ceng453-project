package group10.client.logic;

import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import group10.client.model.PlayerGameEntity;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameLogic {

    private Stack<Card> middle;
    private PlayerEnum lastWinner = PlayerEnum.NULL;
    private Map<PlayerEnum, Integer> scores;
    private Map<PlayerEnum, List<Card>> playerCards;
    private PlayerEnum currentPlayer = PlayerEnum.ONE;
    private int hand = 1;
    private PlayerGameEntity playerGameEntity;

    private static GameLogic instance;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetFields() {
        this.middle = new Stack<>();
        this.scores = new HashMap<>();
        this.scores.put(PlayerEnum.ONE, 0);
        this.scores.put(PlayerEnum.TWO, 0);
        this.playerCards = new HashMap<>();
        this.playerCards.put(PlayerEnum.ONE, new ArrayList<>());
        this.playerCards.put(PlayerEnum.TWO, new ArrayList<>());
    }
    private GameLogic() {
        this.resetFields();
    }

    public Map<PlayerEnum, List<Card>> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
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

    public static void setInstance(GameLogic instance) {
        GameLogic.instance = instance;
    }

    public PlayerEnum getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerEnum currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public PlayerGameEntity getPlayerGameEntity() {
        return playerGameEntity;
    }

    public void setPlayerGameEntity(PlayerGameEntity playerGameEntity) {
        this.playerGameEntity = playerGameEntity;
    }

    public boolean checkIfMatch(Card candidateCard, PlayerEnum player) {
        this.playerCards.get(player).remove(candidateCard);
        if (this.middle.isEmpty()) {
            return false;
        }
        Card topCard = this.middle.peek(); // TODO -- place 4 cards at init
        Integer currentScore = this.scores.get(player);
        System.out.println("check if match - " + candidateCard + " - top : " + topCard);
        if (this.middle.size() == 1 && candidateCard.equals(topCard)) {
            if (candidateCard.getCard() == Cards.JACK) {
                // double pişti :: 20 points
                this.scores.replace(player, currentScore + 20);
            } else {
                // pişti :: 10 points
                this.middle.push(candidateCard);
                int stackScore = this.calculateStackScore(); // pişti with aces, and other special cards
                this.scores.replace(player, currentScore + 10 + stackScore);
            }
            this.middle.empty();
            System.out.println("score for " + player + ": " + this.scores.get(player));
            this.lastWinner = player;
            return true;
        } else if (candidateCard.getCard().equals(topCard.getCard()) || candidateCard.getCard() == Cards.JACK) {
            // empty stack and calculate scores, and save scores
            this.middle.push(candidateCard);
            int stackScore = this.calculateStackScore();
            this.scores.replace(player, currentScore + stackScore);
            this.middle.empty();
            System.out.println("score for " + player + ": " + this.scores.get(player));
            this.lastWinner = player;
            return true;
        } else {
            return false;
        }
    }

    int calculateStackScore() {
        // calculate score of the stack, in case of match
        // each jack - 1
        // each ace -- 1
        // two of clubs -- 2
        // ten of diamonds -- 3
        // TODO -- most cards -- 3 :: equality -> no team
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

    public Pair<Rectangle, Card> playAsComputer(Map<Rectangle, Card> cardMappings) {
        // TODO - this needs numerous implementations, and it already smells a bit?
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

    public boolean isHandEmpty() {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        return cards.isEmpty();
    }

}
