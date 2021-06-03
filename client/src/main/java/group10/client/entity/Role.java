package group10.client.entity;

import java.util.Date;

public class Role extends Base {

    private String name;

    public Role(long id, Date createDate, Date updateDate, boolean active, String operationType, String name) {
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
