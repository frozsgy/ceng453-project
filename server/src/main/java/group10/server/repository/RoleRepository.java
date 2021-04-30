package group10.server.repository;

import group10.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Repository that is used by Roles and PlayerService. Allows communication with the database.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Checks if a role exists by name.
     *
     * @param name - Name of the role to check.
     * @return true if found, false otherwise.
     */
    boolean existsByName(String name);


    /**
     * Finds the Role with the given name.
     *
     * @param name - Name of the role.
     * @return Role if found.
     */
    Role findByName(String name);

}