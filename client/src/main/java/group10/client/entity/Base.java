package group10.client.entity;

import java.util.Date;

/**
 * Base entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Base {

    /**
     * Primary key of field
     */
    private long id;
    /**
     * Creation date of the entry
     */
    private Date createDate;
    /**
     * Update date of the entry. If no updates were made, same as createDate.
     */
    private Date updateDate;
    /**
     * Boolean to mark if the field is active.
     */
    private boolean active;
    /**
     * Field to store the most recent operation type. Can be save, update or delete.
     */
    private String operationType;

    /**
     * Default no parameter constructor
     */
    public Base() {
    }

    /**
     * Gets the id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id new value of id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets create date
     *
     * @return create date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets create date
     *
     * @param createDate new value of create date
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets update date
     *
     * @return update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets update date
     *
     * @param updateDate new value of update date
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets active
     *
     * @return active status
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active
     *
     * @param active new value of active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets operation type
     *
     * @return operation type
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets operation type
     *
     * @param operationType new value of operation type
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
