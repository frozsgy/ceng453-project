package group10.server.entity;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Entity for Match objects.
 */
@Entity
@Table(name = "rounds")
public class Match extends EntityBase {

    /**
     * Field for storing Player information.
     * Since a Player has multiple matches due to multiple levels and games,
     * the relation is ManyToOne.
     */
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    /**
     * Field for storing Game information.
     * Since a Game has multiple matches due to multiple levels,
     * the relation is ManyToOne.
     */
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    /**
     * Field for storing the Match level.
     */
    private int level;

    /**
     * Field for storing score value.
     */
    private int score;

    /**
     * Gets Player of the match.
     *
     * @return Player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets Player
     *
     * @param player player to be set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets Game that the Match is a member of.
     *
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets Game
     *
     * @param game game to be set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Gets score of the match
     *
     * @return Score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score
     *
     * @param score score to be set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets level of the Match
     *
     * @return int
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level of the Match
     *
     * @param level level to be set
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
