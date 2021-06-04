package group10.client.entity;

/**
 * Role entity for parsing JSON responses from the server
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class Role extends Base {

    /**
     * Field for storing name of the role
     */
    private String name;

    /**
     * Default no parameter constructor
     */
    public Role() {
    }

    /**
     * Gets name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }
}
