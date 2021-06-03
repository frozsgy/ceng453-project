package group10.client.model;

public class PasswordReset {

    private String email;
    private String password;
    private String resetCode;
    private String username;

    public PasswordReset(String email) {
        this.email = email;
    }
    public PasswordReset (String username, String resetCode, String password) {
        this.password = password;
        this.resetCode = resetCode;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
