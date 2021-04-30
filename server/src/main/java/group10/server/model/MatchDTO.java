package group10.server.model;

import org.hibernate.validator.constraints.Range;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Data transfer object that is received from client.
 * Carries level information, which are score and game
 */
public class MatchDTO {

    /**
     * Non empty score of the level.
     */
    @Range(min = 0, message= "Score may not be empty")
    private int score;
    /**
     * Non empty game id of this level belongs to.
     */
    @Range(min = 0, message= "Game may not be empty")
    private long game;

    /**
     * Gets the score
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score
     * @param score score to be set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the game
     * @return game of this level
     */
    public long getGame() {
        return game;
    }

    /**
     * Sets the game of this level.
     * @param game Game to be set
     */
    public void setGame(long game) {
        this.game = game;
    }

}
