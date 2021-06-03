package group10.client.entity;

import group10.client.enums.GameTypes;


public class Game extends Base {

    private GameTypes gameType;

    public Game() {
    }

    public GameTypes getGameType() {
        return gameType;
    }

    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
