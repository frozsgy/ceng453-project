package group10.server.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid username")
    private String username;

    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid password")
    private String password;

    @NotBlank
    @Size(max = 255, min = 3, message = "Please enter a valid email")
    @Email
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
