package group10.server.model;

import javax.validation.constraints.NotBlank;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Data transfer object that is received from client.
 * Carries log in information, which are username and password.
 */
public class LoginDTO {
    /**
     * Not blank username
     */
    @NotBlank
    private String username;
    /**
     * Not blank password.
     */
    @NotBlank
    private String password;

    /**
     * Gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the username
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
