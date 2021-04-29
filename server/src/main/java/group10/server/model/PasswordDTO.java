package group10.server.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PasswordDTO {

    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid password")
    private String password;
    private String resetCode;
    private String username;

    public String getPassword() {
        return password;
    }
}
