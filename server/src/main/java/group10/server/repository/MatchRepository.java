package group10.server.repository;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long>  {

    Match findFirstByPlayerAndGameOrderByUpdateDateDesc(Player player, Game game);

}
