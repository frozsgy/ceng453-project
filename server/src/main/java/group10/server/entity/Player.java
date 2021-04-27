package group10.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player extends EntityBase {

    @Column(name = "username", length = 255, unique = true)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "email", length = 255, unique = true)
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