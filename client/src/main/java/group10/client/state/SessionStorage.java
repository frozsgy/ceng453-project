package group10.client.state;


/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Singleton class to hold session information
 * of the logged in user.
 */
public class SessionStorage {

    /**
     * Username of the user.
     */
    private String username;
    /**
     * Token that is returned from the server.
     */
    private String token;

    /**
     * Singleton instance.
     */
    private static SessionStorage instance;

    /**
     * Creates an instance, assigns it to instance field and returns it.
     *
     * @return SessionStorage instance.
     */
    public static SessionStorage getInstance() {
        if (instance == null) {
            instance = new SessionStorage();
        }
        return instance;
    }

    /**
     * Gets the username
     *
     * @return Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     *
     * @param username Username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the token.
     *
     * @return Token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token Token to be set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Logs the user out by setting
     * token and username to null.
     */
    public void logout() {
        this.token = null;
        this.username = null;
    }
}
