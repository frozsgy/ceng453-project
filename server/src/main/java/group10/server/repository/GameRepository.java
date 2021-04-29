package group10.server.repository;

import group10.server.entity.Game;
import group10.server.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Player, Long> {


}
