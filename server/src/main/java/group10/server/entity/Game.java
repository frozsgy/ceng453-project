package group10.server.entity;


import group10.server.enums.GameTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for Game objects.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
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
     * @param gameType game type to be set
     */
    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
