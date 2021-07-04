package group10.client.entity;

import java.io.Serializable;

/**
 * PlayerGame entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class PlayerGame extends Base implements Serializable {

    /**
     * Field for storing Player
     */
    private Player player;

    /**
     * Field for storing Game
     */
    private Game game;

    /**
     * Field for storing level information
     */
    private long level;

    /**
     * Field for storing score information
     */
    private long score;

    /**
     * Default no parameter constructor
     */
    public PlayerGame() {
    }

    /**
     * Gets player
     *
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player
     *
     * @param player new player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets game
     *
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets game
     *
     * @param game new game
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets level
     *
     * @return level
     */
    public long getLevel() {
        return level;
    }

    /**
     * Sets level
     *
     * @param level new level
     */
    public void setLevel(long level) {
        this.level = level;
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
     * @param score new score
     */
    public void setScore(long score) {
        this.score = score;
    }
}
