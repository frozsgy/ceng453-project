package group10.client.entity;

import java.util.Date;
import java.util.List;

public class Player extends Base {

    private String username;
    private String email;
    private List<Role> roles;


    public Player(long id, Date createDate, Date updateDate, boolean active, String operationType, String username, String email, List<Role> roles) {
        super(id, createDate, updateDate, active, operationType);
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
