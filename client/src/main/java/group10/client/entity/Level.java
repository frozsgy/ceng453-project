package group10.client.entity;

/**
 * Level entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Level {

    /**
     * Field for storing game id
     */
    private long game;

    /**
     * Field for storing score
     */
    private long score;

    /**
     * Default no parameter constructor
     */
    public Level() {
    }

    /**
     * Two parameters constructor
     */
    public Level(long game, long score) {
        this.game = game;
        this.score = score;
    }

    /**
     * Gets game id
     *
     * @return game id
     */
    public long getGame() {
        return game;
    }

    /**
     * Sets game id
     *
     * @param game new value of game
     */
    public void setGame(long game) {
        this.game = game;
    }

    /**
     * Gets score
     *
     * @return score
     */
    public long getScore() {
        return score;
    }

    /**
     * Sets score
     *
     * @param score new value of score
     */
    public void setScore(long score) {
        this.score = score;
    }
}
