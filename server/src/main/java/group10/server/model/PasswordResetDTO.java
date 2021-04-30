package group10.server.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordResetDTO {

    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid password")
    private String password;
    @Size(min = 1, message = "Please enter a valid code")
    private String resetCode;
    private String username;

    public String getPassword() {
        return password;
    }

    public String getResetCode() {
        return resetCode;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
