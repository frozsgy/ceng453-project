package group10.server.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Data transfer object that is received from client.
 * Carries password reset information, which are password,
 * reset code and username
 */
public class PasswordResetDTO {

    /**
     * Password that is minimum 3 of size and max 255 of size.
     */
    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid password")
    private String password;

    /**
     * Non empty reset code.
     */
    @Size(min = 1, message = "Please enter a valid code")
    private String resetCode;

    /**
     * Username whose password is tried to be reset.
     */
    private String username;

    /**
     * Gets the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the reset code
     * @return resetCode
     */
    public String getResetCode() {
        return resetCode;
    }

    /**
     * Gets the username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the password
     * @param password password that is going to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the reset code
     * @param resetCode reset code that is going to be set
     */
    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    /**
     * Sets the username
     * @param username reset code that is going to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
