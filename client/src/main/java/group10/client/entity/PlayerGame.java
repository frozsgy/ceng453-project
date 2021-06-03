package group10.client.entity;

import java.util.Date;

public class PlayerGame extends Base {

    private Player player;
    private Game game;
    private long level;
    private long score;

    public PlayerGame(long id, Date createDate, Date updateDate, boolean active, String operationType, Player player, Game game, long level, long score) {
        super(id, createDate, updateDate, active, operationType);
        this.player = player;
        this.game = game;
        this.level = level;
        this.score = score;
    }

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

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
