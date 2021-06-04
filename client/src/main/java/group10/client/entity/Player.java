package group10.client.entity;

import java.util.List;

/**
 * Player entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Player extends Base {

    /**
     * Field for storing username
     */
    private String username;
    /**
     * Field for storing email
     */
    private String email;
    /**
     * Field for storing roles of the user
     */
    private List<Role> roles;

    /**
     * Default no parameter constructor
     */
    public Player() {
    }

    /**
     * Gets username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username
     *
     * @param username new value of username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets roles
     *
     * @return roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles
     *
     * @param roles new roles list
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
