package group10.client.entity;

import group10.client.enums.GameTypes;

/**
 * Game entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Game extends Base {

    /**
     * GameType field
     */
    private GameTypes gameType;

    /**
     * Default no parameter constructor
     */
    public Game() {
    }

    /**
     * Gets game type
     *
     * @return game type
     */
    public GameTypes getGameType() {
        return gameType;
    }

    /**
     * Sets game type
     *
     * @param gameType new game type value
     */
    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
