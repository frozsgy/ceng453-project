package group10.server.repository;

import group10.server.entity.Game;
import group10.server.entity.Match;
import group10.server.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Match findFirstByPlayerAndGameOrderByUpdateDateDesc(Player player, Game game);

    @Query(value = "SELECT SUM(r.score) AS Score, r.player_id AS UserId FROM rounds r " +
            "WHERE r.create_date >= CURDATE() - INTERVAL :days DAY GROUP BY r.player_id ORDER BY Score DESC ",
            countQuery = "SELECT COUNT(*) FROM (SELECT SUM(r.score) AS Score, r.player_id AS UserId FROM rounds r " +
                    "WHERE r.create_date >= CURDATE() - INTERVAL :days DAY GROUP BY r.player_id) k",
            nativeQuery = true)
    Page<Scoreboard> getScoreboard(@Param(value = "days") long days, Pageable pageable);

    List<Match> findByGame(Game game);

}
