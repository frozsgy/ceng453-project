package group10.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Entity Base that is used by all entities, and contains common fields.
 */
@MappedSuperclass
public class EntityBase implements Serializable {

    /**
     * Primary Key that is automatically incremented by every insert.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Create date for the entry.
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * Update date for the entry.
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * Boolean field to state if the entry is soft-deleted or not.
     */
    @Column(name = "active")
    private boolean active;

    /**
     * Field that stores the operation type for the most recent operation that has happened.
     * Can be either save, update or delete.
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * Method that gets invoked before insertion.
     * Sets the operation type to save, marks as active,
     * and fills in the create and update date values.
     */
    @PrePersist
    public void onPrePersist() {
        this.setOperationType("save");
        this.setActive(true);
        this.setCreateDate(new Date());
        this.setUpdateDate(new Date());
    }

    /**
     * Method that gets invoked on update.
     * Sets the operation type to update, and updates the update date value.
     */
    @PreUpdate
    public void onPreUpdate() {
        this.setOperationType("update");
        this.setUpdateDate(new Date());
    }

    /**
     * Method that gets invoked before deletion.
     * Sets the operation type to delete, marks as inactive,
     * and updates the update date value.
     */
    @PreRemove
    public void onPreRemove() {
        this.setOperationType("delete");
        this.setUpdateDate(new Date());
        this.setActive(false);
    }

    /**
     * Gets id.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets create date.
     *
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date.
     *
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets update date.
     *
     * @return updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets update date.
     *
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets active status.
     *
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active status.
     *
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets operation type.
     *
     * @return operationType
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets operation type.
     *
     * @param operationType
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}