package group10.server.repository;

import group10.server.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUsername(String username);
    Optional<Player> findByEmail(String email);
    Optional<Player> getById(long id);

    @Modifying
    @Query("UPDATE Player p SET p.password = :password where p.id = :id")
    void updatePassword(@Param(value = "id") long id, @Param(value = "password") String password);

}
