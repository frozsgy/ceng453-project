package group10.client.logic;

import group10.client.enums.Cards;
import group10.client.enums.PlayerEnum;
import group10.client.enums.Suits;
import group10.client.model.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GameLogic {

    private Stack<Card> middle;
    private PlayerEnum lastWinner = PlayerEnum.NULL;
    private Map<PlayerEnum, Integer> scores;

    private static GameLogic instance;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    private GameLogic() {
        this.middle = new Stack<>();
        this.scores = new HashMap<>();
        this.scores.put(PlayerEnum.ONE, 0);
        this.scores.put(PlayerEnum.TWO, 0);
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

    private boolean checkIfMatch(Card candidateCard, PlayerEnum player) {
        Card topCard = this.middle.peek();
        Integer currentScore = this.scores.get(player);
        if (this.middle.size() == 1 && candidateCard.equals(topCard)) {
            // TODO -- what if pişti with two of clubs and another two? a better implementation needed here
            if (candidateCard.getCard() == Cards.JACK) {
                // TODO -- double pişti :: 20 points
                this.scores.replace(player, currentScore + 20);
            } else {
                // TODO -- pişti :: 10 points
                this.middle.push(candidateCard);
                int stackScore = this.calculateStackScore(); // pişti with aces, and other special cards
                this.scores.replace(player, currentScore + 10 + stackScore);
            }
            this.middle.empty();
            return true;
        } else if (candidateCard.equals(topCard) || candidateCard.getCard() == Cards.JACK) {
            // TODO -- empty stack and calculate scores, and save scores
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


}
