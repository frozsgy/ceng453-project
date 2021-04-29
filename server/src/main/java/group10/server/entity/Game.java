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

    /*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "matches", joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")})
    @JsonManagedReference
    private List<Player> players;
    */


}
