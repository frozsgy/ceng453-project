package group10.server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Entity for Role objects.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Entity
@Table(name = "role")
public class Role extends EntityBase {

    /**
     * Field for storing role name. Must be unique.
     */
    @Column(name = "name", length = 255, unique = true)
    private String name;

    /**
     * Relation for storing Role information. Since a user can have many roles,
     * and since roles can belong to multiple users, the relation is ManyToMany.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<Player> players;

    /**
     * Gets name of the role.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name of the role.
     *
     * @param name name to be set for the role
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets Players with roles.
     *
     * @return List of Player
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Sets Players with roles.
     *
     * @param players roles to be set for players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}