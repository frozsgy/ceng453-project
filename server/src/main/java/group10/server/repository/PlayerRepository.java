package group10.server.repository;

import group10.server.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUsername(String username);
    Optional<Player> findByEmail(String email);
    Optional<Player> getById(long id);

}
