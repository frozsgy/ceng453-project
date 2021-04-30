package group10.server.repository;

import group10.server.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Repository that is used by PlayerService. Allows communication with the database.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Finds the Player with the given username.
     *
     * @param username - Username to find
     * @return Player if found.
     */
    Optional<Player> findByUsername(String username);

    /**
     * Finds the Player with the given e-mail.
     *
     * @param email - Email to find
     * @return Player if found.
     */
    Optional<Player> findByEmail(String email);

    /**
     * Finds the Player with the given user id.
     *
     * @param id - Username id to find
     * @return Player if found.
     */
    Optional<Player> getById(long id);


    /**
     * Updates the password of a registered user.
     *
     * @param id       - User id to update the password of
     * @param password - New password
     */
    @Modifying
    @Query("UPDATE Player p SET p.password = :password where p.id = :id")
    void updatePassword(@Param(value = "id") long id, @Param(value = "password") String password);

}
