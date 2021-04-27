package group10.server.model;

import javax.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
