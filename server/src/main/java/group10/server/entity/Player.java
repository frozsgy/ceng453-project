package group10.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


/**
 * Entity for Player objects.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
@Entity
@Table(name = "player")
public class Player extends EntityBase {

    /**
     * Field for storing username. Must be unique.
     */
    @Column(name = "username", length = 255, unique = true)
    private String username;

    /**
     * Field for storing password. Ignored in JSON responses to keep passwords hashes safe.
     */
    @Column(name = "password", length = 255)
    @JsonIgnore
    private String password;

    /**
     * Field for storing email. Must be unique.
     */
    @Column(name = "email", length = 255, unique = true)
    private String email;

    /**
     * Relation for storing Role information. Since a user can have many roles,
     * and since roles can belong to multiple users, the relation is ManyToMany.
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "player_roles", joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    @JsonManagedReference
    private List<Role> roles;

    /**
     * Gets username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username username to be set for the player
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password password to be set for the player
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email email to be set for the player
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets roles.
     *
     * @return List of Roles.
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles roles to be set for the player
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}