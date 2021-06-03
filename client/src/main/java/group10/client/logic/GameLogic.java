package group10.client.logic;

import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;
import group10.client.entity.PlayerGame;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameLogic {

    private ArrayList<Card> middle;
    private PlayerEnum lastWinner = PlayerEnum.NULL;
    private Map<PlayerEnum, Integer> scores;
    private Map<PlayerEnum, List<Card>> playerCards;
    private PlayerEnum currentPlayer = PlayerEnum.ONE;
    private int hand = 1;
    private PlayerGame playerGameEntity;
    private AiStrategy strategy;

    private static GameLogic instance;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void resetFields() {
        this.middle = new ArrayList<>();
        this.scores = new HashMap<>();
        this.scores.put(PlayerEnum.ONE, 0);
        this.scores.put(PlayerEnum.TWO, 0);
        this.playerCards = new HashMap<>();
        this.playerCards.put(PlayerEnum.ONE, new ArrayList<>());
        this.playerCards.put(PlayerEnum.TWO, new ArrayList<>());
    }
    private GameLogic() {
        this.resetFields();
        this.setAiStrategy(1);
    }

    public void setAiStrategy(int level) {
        if (level == 1) {
            strategy = new LevelOneStrategy(this.playerCards, this.middle);
        } else if (level == 2){
            strategy = new LevelTwoStrategy(this.playerCards, this.middle);
        } else {
            strategy = new LevelThreeStrategy(this.playerCards, this.middle);
        }
    }

    public Map<PlayerEnum, List<Card>> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Map<PlayerEnum, List<Card>> playerCards) {
        this.playerCards = playerCards;
    }

    public ArrayList<Card> getMiddle() {
        return middle;
    }

    public void setMiddle(ArrayList<Card> middle) {
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

    public PlayerGame getPlayerGameEntity() {
        return playerGameEntity;
    }

    public void setPlayerGameEntity(PlayerGame playerGameEntity) {
        this.playerGameEntity = playerGameEntity;
    }

    public AiStrategy getAiStrategy() {return this.strategy;}

    public boolean checkIfMatch(Card candidateCard, PlayerEnum player) {
        this.playerCards.get(player).remove(candidateCard);
        if (this.middle.isEmpty()) {
            return false;
        }
        Card topCard = this.middle.get(this.middle.size() - 1);
        Integer currentScore = this.scores.get(player);
        System.out.println("check if match - " + candidateCard + " - top : " + topCard);
        if (this.middle.size() == 1 && candidateCard.equals(topCard)) {
            if (candidateCard.getCard() == Cards.JACK) {
                // double pişti :: 20 points
                this.scores.replace(player, currentScore + 20);
            } else {
                // pişti :: 10 points
                this.middle.add(candidateCard);
                int stackScore = this.calculateStackScore(); // pişti with aces, and other special cards
                this.scores.replace(player, currentScore + 10 + stackScore);
            }
            this.middle.clear();
            System.out.println("score for " + player + ": " + this.scores.get(player));
            this.lastWinner = player;
            return true;
        } else if (candidateCard.getCard().equals(topCard.getCard()) || candidateCard.getCard() == Cards.JACK) {
            // empty stack and calculate scores, and save scores
            this.middle.add(candidateCard);
            int stackScore = this.calculateStackScore();
            this.scores.replace(player, currentScore + stackScore);
            this.middle.clear();
            System.out.println("score for " + player + ": " + this.scores.get(player));
            this.lastWinner = player;
            return true;
        } else {
            return false;
        }
    }

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
            Card pop = this.middle.get(this.middle.size() - 1);
            this.middle.remove(this.middle.size() - 1);
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

    public boolean isHandEmpty() {
        List<Card> cards = this.playerCards.get(PlayerEnum.TWO);
        return cards.isEmpty();
    }

    public static Rectangle getRectangleByCard(Map<Rectangle, Card> cardMappings, Card card) {
        Rectangle r = null;
        for (Map.Entry<Rectangle, Card> entry: cardMappings.entrySet()) {
            if (card.equals(entry.getValue())) {
                r = entry.getKey();
            }
        }
        return r;
    }

    public void giveMidStackCardsToLastWinner() {
        int stackScore = this.calculateStackScore();
        List<Card> cards = this.playerCards.get(this.lastWinner);
        while (!this.middle.isEmpty()) {
            Card pop = this.middle.get(this.middle.size() - 1);
            this.middle.remove(this.middle.size() - 1);
            cards.add(pop);
        }
        this.playerCards.replace(this.lastWinner, cards);
        this.scores.replace(this.lastWinner, this.scores.get(this.lastWinner) + stackScore);
        if (this.scores.get(PlayerEnum.ONE) > this.scores.get(PlayerEnum.TWO)) {
            this.scores.replace(PlayerEnum.ONE, this.scores.get(PlayerEnum.ONE) + 3);
        } else if (this.scores.get(PlayerEnum.ONE) < this.scores.get(PlayerEnum.TWO)) {
            this.scores.replace(PlayerEnum.TWO, this.scores.get(PlayerEnum.TWO) + 3);
        }

    }
}
