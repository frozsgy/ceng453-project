package group10.server.repository;

import group10.server.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Repository that is used by GameService. Allows communication with the database.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Finds the Game with the given Game ID.
     *
     * @param id Game ID
     * @return Optional Game - if found
     */
    Optional<Game> findById(long id);

}
