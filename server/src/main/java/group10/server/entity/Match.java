package group10.server.entity;


import javax.persistence.*;

@Entity
@Table(name = "score")
public class Match extends EntityBase {

    @ManyToOne
    @JoinColumn(name="player_id", nullable=false)
    private Player player;

    @ManyToOne
    @JoinColumn(name="game_id", nullable = false)
    private Game game;

    private int score;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
