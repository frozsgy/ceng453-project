package group10.server.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Data transfer object that is received from client.
 * Carries player information to register
 */
public class PlayerDTO {

    /**
     * Username of min. size 3 max size 255
     */
    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid username")
    private String username;

    /**
     * Password of min. size 3 max size 255
     */
    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid password")
    private String password;

    /**
     * Email of min. size 3 max size 255
     */
    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid email")
    @Email(message="Please enter a valid email")
    private String email;

    /**
     * Gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username username to be set
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
     * @param password password to be set
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
     * @param email email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
