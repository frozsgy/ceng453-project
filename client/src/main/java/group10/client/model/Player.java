package group10.client.model;

/**
 *  @author Alperen Caykus, Mustafa Ozan Alpay
 *  Registering and logging-in player model to log in and sign up
 */
public class Player {

    /**
     * Username of the player
     */
    private String username;
    /**
     * Password of the player
     * Hashing of password is performed in method that makes HTTP request.
     */
    private String password;
    /**
     * Email of the player
     */
    private String email;

    /**
     * Constructor for Player when logging in
     * @param username Username
     * @param password Password
     */
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for Player when registering
     * @param username Username
     * @param password Password
     * @param email Email
     */
    public Player(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username Username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password Password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     * @param email Email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
