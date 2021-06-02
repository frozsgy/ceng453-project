package group10.client.model;

import java.util.Date;

public class RoleEntity extends BaseEntity {

    private String name;

    public RoleEntity(long id, Date createDate, Date updateDate, boolean active, String operationType, String name) {
        super(id, createDate, updateDate, active, operationType);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
