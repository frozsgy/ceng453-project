package group10.client.model;

import group10.client.enums.GameTypes;

import java.util.Date;


public class GameEntity extends BaseEntity {

    private GameTypes gameType;

    public GameEntity(long id, Date createDate, Date updateDate, boolean active, String operationType, GameTypes gameType) {
        super(id, createDate, updateDate, active, operationType);
        this.gameType = gameType;
    }

    public GameTypes getGameType() {
        return gameType;
    }

    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
