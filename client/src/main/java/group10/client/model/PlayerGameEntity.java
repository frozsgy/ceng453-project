package group10.client.model;

import java.util.Date;

public class PlayerGameEntity extends BaseEntity {

    private PlayerEntity player;
    private GameEntity game;
    private long level;
    private long score;

    public PlayerGameEntity(long id, Date createDate, Date updateDate, boolean active, String operationType, PlayerEntity player, GameEntity game, long level, long score) {
        super(id, createDate, updateDate, active, operationType);
        this.player = player;
        this.game = game;
        this.level = level;
        this.score = score;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
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
