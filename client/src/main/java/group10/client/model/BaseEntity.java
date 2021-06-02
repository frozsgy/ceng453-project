package group10.client.model;

import java.util.Date;

public class BaseEntity {

    private long id;
    private Date createDate;
    private Date updateDate;
    private boolean active;
    private String operationType;

    public BaseEntity() {
    }

    public BaseEntity(long id, Date createDate, Date updateDate, boolean active, String operationType) {
        this.id = id;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.active = active;
        this.operationType = operationType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
