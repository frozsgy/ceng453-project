package group10.client.model;

import java.util.Date;
import java.util.List;

public class PlayerEntity extends BaseEntity {

    private String username;
    private String email;
    private List<RoleEntity> roles;


    public PlayerEntity(long id, Date createDate, Date updateDate, boolean active, String operationType, String username, String email, List<RoleEntity> roles) {
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

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
}
