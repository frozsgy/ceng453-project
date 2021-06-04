package group10.client.model;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Password reset class that is converted to Json and
 * sent to server when doing password update.
 */
public class PasswordReset {

    /**
     * Email of the registered user.
     */
    private String email;
    /**
     * New password to be set.
     * Hashing of password is performed in method that makes HTTP request.
     */
    private String password;
    /**
     * Reset code that is acquired via email.
     * This code is sent to email given in the email field if a matching user is found.
     */
    private String resetCode;
    /**
     * Username of the registered user that is updating their password.
     */
    private String username;

    /**
     * Constructor for PasswordReset.
     * This is used when acquiring the reset code.
     *
     * @param email Email that the reset code will be sent to.
     */
    public PasswordReset(String email) {
        this.email = email;
    }

    /**
     * Constructor for PasswordReset.
     * This is used after acquiring the reset code and sending the new password.
     *
     * @param username  Username of the account whose password is being changed.
     * @param resetCode Resetcode that is sent to email.
     * @param password  New password to be set.
     */
    public PasswordReset(String username, String resetCode, String password) {
        this.password = password;
        this.resetCode = resetCode;
        this.username = username;
    }

    /**
     * Gets the username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     *
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
