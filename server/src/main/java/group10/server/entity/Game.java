package group10.server.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import group10.server.enums.GameTypes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game")
public class Game extends EntityBase {

    @Column(name = "game_type")
    private GameTypes gameType;

    public GameTypes getGameType() {
        return gameType;
    }

    public void setGameType(GameTypes gameType) {
        this.gameType = gameType;
    }
}
