package group10.server.entity;


import group10.server.enums.GameTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Entity for Game objects.
 */
@Entity
@Table(name = "game")
public class Game extends EntityBase {

    /**
     * Field that stores GameType.
     *
     * @see GameTypes
     */
    @Column(name = "game_type")
    private GameTypes gameType;

    /**
     * Gets Game Type.
     *
     * @return GameType
     */
    public GameTypes getGameType() {
        return gameType;
    }

    /**
     * Sets Game Type.
     *
     * @param gameType
     */
    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
